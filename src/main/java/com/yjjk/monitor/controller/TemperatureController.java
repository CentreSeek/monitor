/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: PatientController
 * Author:   CentreS
 * Date:     2019/7/19 9:20
 * Description: 病人管理模块
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.controller;

import com.alibaba.fastjson.JSON;
import com.yjjk.monitor.configer.CommonResult;
import com.yjjk.monitor.constant.ErrorCodeEnum;
import com.yjjk.monitor.constant.MachineConstant;
import com.yjjk.monitor.constant.TemperatureConstant;
import com.yjjk.monitor.entity.ZsMachineInfo;
import com.yjjk.monitor.entity.ZsManagerInfo;
import com.yjjk.monitor.entity.ZsPatientInfo;
import com.yjjk.monitor.entity.ZsPatientRecord;
import com.yjjk.monitor.entity.export.RecordHistory2Excel;
import com.yjjk.monitor.entity.json.TemperatureHistory;
import com.yjjk.monitor.entity.param.TemperatureBound;
import com.yjjk.monitor.entity.transaction.BackgroundResult;
import com.yjjk.monitor.entity.transaction.BackgroundSend;
import com.yjjk.monitor.entity.vo.RecordHistory;
import com.yjjk.monitor.entity.vo.TemperatureBoundVO;
import com.yjjk.monitor.entity.vo.UseMachineVO;
import com.yjjk.monitor.utility.DateUtil;
import com.yjjk.monitor.utility.ResultUtil;
import com.yjjk.monitor.utility.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CentreS
 * @Description: 体温管理模块
 * @create 2019/7/19
 */
@Api(tags = {"体温管理模块"})
@RestController
@RequestMapping("patient")
public class TemperatureController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemperatureController.class);

    /**
     * 启用设备
     *
     * @param bedId
     * @param machineId
     * @param name
     * @param caseNum
     * @param request
     * @param response
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "启用设备")
    @RequestMapping(value = "/patient", method = RequestMethod.POST)
    public synchronized void startMachine(@RequestParam(value = "bedId") Integer bedId,
                                          @RequestParam(value = "machineId") Integer machineId,
                                          @RequestParam(value = "name") String name,
                                          @RequestParam(value = "caseNum") String caseNum,
                                          @RequestParam(value = "managerId") Integer managerId,
                                          @ApiParam(value = "监测类型 0：体温 1：心率、呼吸率", required = true) @RequestParam("recordType") Integer recordType,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";

        ZsManagerInfo managerInfo = this.managerService.getManagerInfo(managerId);
        ZsPatientInfo zsPatientInfo1 = this.patientService.selectByCaseNum(caseNum);
        if (zsPatientInfo1 != null) {
            ZsPatientRecord zsPatientRecord = this.patientRecordService.selectByPatientId(zsPatientInfo1.getPatientId());
            if (zsPatientRecord != null) {
                message = "该病人已在其他床位启用设备";
                returnResult(startTime, request, response, resultCode, message, "");
                return;
            }
            zsPatientInfo1.setName(name);
            zsPatientInfo1.setBedId(bedId);

            this.patientService.updateName(zsPatientInfo1);
        }
        ZsPatientInfo zsPatientInfo = this.patientService.addPatient(name, caseNum, bedId, managerInfo.getDepartmentId());
        if (StringUtils.isNullorEmpty(zsPatientInfo)) {
            message = "新增病人信息失败";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }

        ZsPatientRecord patientRecord = new ZsPatientRecord();
        patientRecord.setBedId(bedId).setRecordType(recordType).setStartTime(DateUtil.getCurrentTime()).setMachineId(machineId).
                setPatientId(zsPatientInfo.getPatientId());
        int i = this.patientRecordService.addPatientRecord(patientRecord);
        if (i == 0) {
            message = "新增使用信息失败";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        // 连接心电设备
        BackgroundResult backgroundResult = super.ecgService.connectEcgMachine(machineId, bedId, BackgroundSend.DATA_CONNECTION);
        if ("false".equals(backgroundResult.getSuccess())) {
            message = backgroundResult.getMessage();
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        ZsMachineInfo machineInfo = new ZsMachineInfo();
        machineInfo.setMachineId(machineId).setUsageState(MachineConstant.USAGE_STATE_USED);
        super.machineService.updateByMachineId(machineInfo);
        message = "成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, "");
    }

    /**
     * 检索病人信息
     *
     * @param name
     * @param caseNum
     * @param request
     * @param response`
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public synchronized void checkPatient(@RequestParam(value = "name") String name,
                                          @RequestParam(value = "caseNum") String caseNum,
                                          HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";

        ZsPatientInfo zsPatientInfo1 = super.patientService.selectByCaseNum(caseNum);
        if (zsPatientInfo1 != null && zsPatientInfo1.getName() != name) {
            message = "该病例已存在";
            returnResult(startTime, request, response, resultCode, message, zsPatientInfo1);
            return;
        }
        message = "成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, "");
    }


    /**
     * 更换设备
     *
     * @param recordId
     * @param machineId
     * @param request
     * @param response
     */
    @RequestMapping(value = "/patient", method = RequestMethod.PUT)
    public void changeMachine(@RequestParam(value = "recordId") Long recordId,
                              @RequestParam(value = "machineId") Integer machineId,
                              HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";

        ZsPatientRecord patientRecord = super.patientRecordService.selectByPrimaryKey(recordId);
        if (StringUtils.isNullorEmpty(patientRecord)) {
            message = "获取历史记录失败";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        boolean b = super.patientRecordService.changeMachine(patientRecord.getMachineId(), machineId);
        patientRecord.setMachineId(machineId);
        int i = super.patientRecordService.updateByPrimaryKey(patientRecord);
        if (i == 0 && b) {
            message = "绑定失败";
            returnResult(startTime, request, response, resultCode, message, i);
            return;
        }
        message = "绑定成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, i);
    }

    /**
     * 停止检测
     *
     * @param recordId
     * @param request
     * @param response
     */
    @RequestMapping(value = "/record", method = RequestMethod.PUT)
    public void stopRecord(@RequestParam(value = "recordId") Long recordId,
                           HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";

        ZsPatientRecord patientRecord = this.patientRecordService.selectByPrimaryKey(recordId);
        if (StringUtils.isNullorEmpty(patientRecord)) {
            message = "未找到该条记录";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        int i = this.patientRecordService.stopMonitoring(patientRecord);
        if (i == 0) {
            message = "停用失败";
            returnResult(startTime, request, response, resultCode, message, Integer.valueOf(i));
            return;
        }
        message = "停用成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, Integer.valueOf(i));
    }

    /**
     * 获取监控信息
     */
    @ApiOperation("获取监控信息")
    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public CommonResult<List<UseMachineVO>> getMinitors(@ApiParam(value = "管理员id", required = true) @RequestParam(value = "managerId") Integer
                                                                managerId,
                                                        @ApiParam(value = "使用中设备0：使用中 1：未使用") @RequestParam(value = "used", required = false) Integer used,
                                                        @ApiParam(value = "起始床位id") @RequestParam(value = "start", required = false) Integer start,
                                                        @ApiParam(value = "结束床位id") @RequestParam(value = "end", required = false) Integer end) {
        /********************** 参数初始化 **********************/

        ZsManagerInfo managerInfo = this.managerService.getManagerInfo(managerId);
        Integer departmentId = null;
        if (managerInfo.getRole().intValue() == 2) {
            departmentId = managerInfo.getDepartmentId();
        }
        List<UseMachineVO> monitorsInfo = this.patientRecordService.getMonitorsInfo(departmentId);
        monitorsInfo = this.patientRecordService.updateTemperature(monitorsInfo, departmentId);

        monitorsInfo = this.patientRecordService.selectiveByBedId(monitorsInfo, Integer.valueOf(start == null ? 0 : start.intValue()),
                Integer.valueOf(end == null ? Integer.MAX_VALUE : end.intValue()));
        if ((used != null) && (used.intValue() == 0)) {
            monitorsInfo = this.patientRecordService.isUsed(monitorsInfo);
        }
        for (int i = 0; i < monitorsInfo.size(); i++) {
            (monitorsInfo.get(i)).setPatientName(StringUtils.replaceNameX((monitorsInfo.get(i)).getPatientName()));
        }
        monitorsInfo = this.boxService.setBoxesInfo(monitorsInfo);

        monitorsInfo = this.temperatureBoundService.updateUseMachine(monitorsInfo, departmentId);
        return ResultUtil.returnSuccess(monitorsInfo);
    }

//    /**
//     * 获取实时监控信息
//     *
//     * @param request
//     * @param response
//     */
//    @RequestMapping(value = "/current", method = RequestMethod.GET)
//    public void getCurrentInfo(@RequestParam(value = "managerId") Integer managerId,
//                               HttpServletRequest request, HttpServletResponse response) {
//        /********************** 参数初始化 **********************/
//        long startTime = System.currentTimeMillis();
//        boolean resultCode = false;
//        String message = "";
//
//        ZsManagerInfo managerInfo = super.managerService.getManagerInfo(managerId);
//        if (managerInfo == null){
//            message = "未查询到管理员个人信息";
//            returnResult(startTime, request, response, resultCode, message, "");
//            return;
//        }
//        Integer departmentId = null;
//        if (managerInfo.getRole() == 2) {
//            departmentId = managerInfo.getDepartmentId();
//        }
//        List<PatientTemperature> monitorsTemperature = super.patientRecordService.getMinitorsTemperature(departmentId);
//        if (StringUtils.isNullorEmpty(monitorsTemperature)) {
//            message = "更新失败";
//            returnResult(startTime, request, response, resultCode, message, "");
//            return;
//        }
//        message = "更新成功";
//        resultCode = true;
//        returnResult(startTime, request, response, resultCode, message, monitorsTemperature);
//    }

    /**
     * 查询历史记录
     *
     * @param recordHistory
     * @param request
     * @param response
     */
    @RequestMapping(value = "/record", method = RequestMethod.GET)
    public void getRecordHistory(RecordHistory recordHistory, HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";
        Map<String, Object> map = new HashMap();
        if ((!StringUtils.isNullorEmpty(recordHistory.getCurrentPage())) && (!StringUtils.isNullorEmpty(recordHistory.getPageSize()))) {
            if (recordHistory.getCurrentPage().intValue() <= 0) {
                message = "页码出错";
                returnResult(startTime, request, response, resultCode, message, "");
                return;
            }
            int currentPage = recordHistory.getCurrentPage().intValue();
            int pageSize = recordHistory.getPageSize().intValue();

            int totalCount = this.patientRecordService.getRecordHistoryCount(recordHistory);

            int startLine = (currentPage - 1) * pageSize;

            int totalPage = (totalCount + pageSize - 1) / pageSize;
            recordHistory.setStartLine(Integer.valueOf(startLine));
            map.put("totalPage", Integer.valueOf(totalPage));
            map.put("currentPage", Integer.valueOf(currentPage));
        }
        List<RecordHistory> list = this.patientRecordService.getRecordHistory(recordHistory);
        map.put("list", list == null ? "" : list);
        message = "查询成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, map);
    }

    /**
     * 查询体温历史记录
     *
     * @param recordId
     * @param request
     * @param response
     */
    @RequestMapping(value = "/temperature", method = RequestMethod.GET)
    public void getTemperatureHistory(@RequestParam(value = "recordId") Long recordId,
                                      HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";
        Map<String, Object> reqMap = new HashMap(2);
        Map<String, Object> paraMap = new HashMap();

        ZsPatientRecord patientRecord = this.patientRecordService.selectByPrimaryKey(recordId);
        if (StringUtils.isNullorEmpty(patientRecord)) {
            message = "未找到该条记录";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        String endTime = patientRecord.getEndTime();
        if (StringUtils.isNullorEmpty(endTime)) {
            paraMap.put("endTime", DateUtil.getCurrentTime());
        } else {
            paraMap.put("endTime", endTime);
        }
        paraMap.put("patientId", patientRecord.getPatientId());
        paraMap.put("times", DateUtil.timeDifferentLong(patientRecord.getStartTime(), (String) paraMap.get("endTime")));

        List<TemperatureHistory> list = this.patientRecordService.getCurrentTemperatureRecord(paraMap);
        if (StringUtils.isNullorEmpty(patientRecord.getTemperatureHistory())) {
            reqMap.put("useTimes", DateUtil.timeDifferent(patientRecord.getStartTime()));
        } else {
            list = JSON.parseArray(patientRecord.getTemperatureHistory(), TemperatureHistory.class);
            reqMap.put("useTimes", DateUtil.timeDifferent(patientRecord.getStartTime(), patientRecord.getEndTime()));
        }
        reqMap = this.patientRecordService.parseTemperature(list, reqMap, patientRecord.getMachineId());
        reqMap.put("list", StringUtils.isNullorEmpty(list) ? "" : list);
        reqMap.put("startTime", StringUtils.isNullorEmpty(list) ? "" : DateUtil.integerForward(((TemperatureHistory) list.get(0)).getDateTime()));
        reqMap.put("endTime", StringUtils.isNullorEmpty(list) ? "" :
                DateUtil.integerForward((list.get(list.size() - 1)).getDateTime()));
        message = "查询成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, reqMap);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportTemperatureHistory(@RequestParam(value = "timeList") List<String> timeList,
                                         @RequestParam(value = "token") String token,
                                         HttpServletResponse response) throws IOException {
        if (timeList.size() == 0) {
            return;
        }
        Map<String, Object> paraMap = new HashMap<>();
        List<RecordHistory2Excel> list = new ArrayList<>();
        for (int i = 0; i < timeList.size(); i++) {
            paraMap.put("time", timeList.get(i));
            paraMap.put("threshold", DateUtil.getTwoMinutePast(timeList.get(i)));
            paraMap.put("token", token);
            list.addAll(super.patientRecordService.getExportList(paraMap));
            paraMap.clear();
        }

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("sheet1");

        HSSFRow row = null;
        // 创建第一个单元格
        row = sheet.createRow(0);
        row.setHeight((short) (26.25 * 20));
        // 为第一行单元格设值
        row.createCell(0).setCellValue("温度监测平台使用日志");

        /*
         * 为标题设计空间
         * firstRow从第1行开始
         * lastRow从第0行结束
         *
         * 从第1个单元格开始
         * 从第3个单元格结束
         */
        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 6);
        sheet.addMergedRegion(rowRegion);

        /*
         * 动态获取数据库列 sql语句 select COLUMN_NAME from INFORMATION_SCHEMA.Columns where table_name='user' and table_schema='test'
         * 第一个table_name 表名字
         * 第二个table_name 数据库名称
         * */
        row = sheet.createRow(1);
        //设置行高
        row.setHeight((short) (22.50 * 20));
        //为单元格设值
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("住院号");
        row.createCell(2).setCellValue("科室");
        row.createCell(3).setCellValue("房号");
        row.createCell(4).setCellValue("床位");
        row.createCell(5).setCellValue("时间点");
        row.createCell(6).setCellValue("体温");

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 2);
            RecordHistory2Excel record = list.get(i);
            row.createCell(0).setCellValue(record.getPatientName());
            row.createCell(1).setCellValue(record.getCaseNum());
            row.createCell(2).setCellValue(record.getDepartmentName());
            row.createCell(3).setCellValue(record.getRoom());
            row.createCell(4).setCellValue(record.getBed());
            row.createCell(5).setCellValue(record.getTime());
            row.createCell(6).setCellValue(record.getTemperature());
        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        //列宽自适应
        for (int i = 0; i <= 13; i++) {
            sheet.autoSizeColumn(i);
        }
//        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setContentType("application/octet-stream;charset=utf-8");
        OutputStream os = response.getOutputStream();
        //默认Excel名称
        response.setHeader("Content-disposition", "attachment;filename=" + DateUtil.getHistoryFileName() + ".xls");
        wb.write(os);
        wb.close();
//        os.flush();
//        os.close();
    }

    @ApiOperation("设置体温监测规则")
    @RequestMapping(value = "/bound", method = RequestMethod.PUT)
    public CommonResult setTemperatureAlert(@Valid TemperatureBound param) {
        try {

            /********************** 参数初始化 **********************/
            if (param.getDepartmentId().equals(TemperatureConstant.DEFAULT_DEPARTMENT_ID)) {
                return ResultUtil.returnError(ErrorCodeEnum.TEMPERATURE_BOUND_DEPARTMENT_ERROR);
            }
            Integer i = super.temperatureBoundService.setTemperatureBound(param);
            return ResultUtil.returnSuccess(i);
        } catch (Exception e) {
            LOGGER.error("业务异常信息：[{}]", e.getMessage(), e);
            return ResultUtil.returnError(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @ApiOperation("获取默认体温监测规则")
    @RequestMapping(value = "/bound", method = RequestMethod.GET)
    public CommonResult<List<TemperatureBoundVO>> getDefaultAlert(@RequestParam(value = "departmentId") Integer departmentId) {
        try {
            /********************** 参数初始化 **********************/
            List<TemperatureBoundVO> defaultAlert = super.temperatureBoundService.getDefaultAlert(departmentId);
            return ResultUtil.returnSuccess(defaultAlert);
        } catch (Exception e) {
            LOGGER.error("业务异常信息：[{}]", e.getMessage(), e);
            return ResultUtil.returnError(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @ApiOperation("更换床位")
    @RequestMapping(value = {"/bed"}, method = {org.springframework.web.bind.annotation.RequestMethod.PUT})
    public CommonResult changeBed(@ApiParam(value = "recordId", required = true) @RequestParam("recordId") Long recordId,
                                  @ApiParam(value = "新床位号", required = true) @RequestParam("newBedId") Integer newBedId) {
        try {
            ZsPatientRecord patientRecord = this.patientRecordService.selectByPrimaryKey(recordId);
            if (StringUtils.isNullorEmpty(patientRecord)) {
                return ResultUtil.returnError(ErrorCodeEnum.NON_RECORD);
            }
            ZsPatientRecord zsPatientRecord = new ZsPatientRecord();
            zsPatientRecord.setBedId(newBedId);
            zsPatientRecord.setRecordId(recordId);
            int i = this.patientRecordService.updateByPrimaryKey(zsPatientRecord);
            if (i == 1) {
                return ResultUtil.returnSuccess(Integer.valueOf(i));
            }
            return ResultUtil.returnError(ErrorCodeEnum.UPDATE_ERROR);
        } catch (Exception e) {
            LOGGER.error("业务异常信息：[{}]", e.getMessage(), e);
        }
        return ResultUtil.returnError(ErrorCodeEnum.UNKNOWN_ERROR);
    }

    //    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public void test() {
//        try {
//            /********************** 参数初始化 **********************/
//            String date = DateUtil.getOneMonthAgo();
//            super.hospitalService.temperatureInfoPersistent(date);
//        } catch (Exception e) {
//            LOGGER.error("业务异常信息：[{}]", e.getMessage(), e);
//        }
//    }

}
