/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: HeartRateController
 * Author:   CentreS
 * Date:     2019/11/11 9:32
 * Description: 心率模块
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.controller;

import com.alibaba.fastjson.JSON;
import com.yjjk.monitor.configer.CommonResult;
import com.yjjk.monitor.constant.ErrorCodeEnum;
import com.yjjk.monitor.constant.MachineConstant;
import com.yjjk.monitor.constant.PatientRecordConstant;
import com.yjjk.monitor.entity.ZsMachineInfo;
import com.yjjk.monitor.entity.ZsManagerInfo;
import com.yjjk.monitor.entity.ZsPatientRecord;
import com.yjjk.monitor.entity.export.HealthRecordHistory2Excel;
import com.yjjk.monitor.entity.json.HealthHistory;
import com.yjjk.monitor.entity.transaction.BackgroundResult;
import com.yjjk.monitor.entity.transaction.BackgroundSend;
import com.yjjk.monitor.entity.vo.EcgMonitorVO;
import com.yjjk.monitor.entity.vo.HealthHistoryVO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CentreS
 * @Description: 心率模块
 * @create 2019/11/11
 */
@Api(tags = {"心率模块"})
@RestController
@RequestMapping("heart")
public class EcgController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EcgController.class);

    
    @ApiOperation("心电监测信息")
    @RequestMapping(value = {"/monitor"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public CommonResult<List<EcgMonitorVO>> getMonitors(@ApiParam(value = "管理员", required = true) @RequestParam("managerId") Integer managerId,
                                                        @ApiParam("使用中设备 0：使用中 1：未使用") @RequestParam(value = "used", required = false) Integer used,
                                                        @ApiParam("起始床位id") @RequestParam(value = "start", required = false) Integer start,
                                                        @ApiParam("结束床位id") @RequestParam(value = "end", required = false) Integer end) {
        ZsManagerInfo managerInfo = this.managerService.getManagerInfo(managerId);
        Integer departmentId = null;
        if (managerInfo.getRole().intValue() == 2) {
            departmentId = managerInfo.getDepartmentId();
        }
        List<UseMachineVO> monitorsInfo = this.ecgService.getMonitorsInfo(departmentId);

        monitorsInfo = this.patientRecordService.selectiveByBedId(monitorsInfo, Integer.valueOf(start == null ? 0 : start.intValue()),
                Integer.valueOf(end == null ? Integer.MAX_VALUE : end.intValue()));
        if ((used != null) && (used.intValue() == 0)) {
            monitorsInfo = this.patientRecordService.isUsed(monitorsInfo);
        }
        for (int i = 0; i < monitorsInfo.size(); i++) {
            (monitorsInfo.get(i)).setPatientName(StringUtils.replaceNameX((monitorsInfo.get(i)).getPatientName()));
        }
        List<EcgMonitorVO> list = this.ecgService.updateUseMachine(monitorsInfo, departmentId);
        return ResultUtil.returnSuccess(list);
    }

    @ApiOperation("心率、呼吸率历史记录")
    @RequestMapping(value = {"/healthHistory"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public CommonResult<HealthHistoryVO> getHealthHistory(@RequestParam("recordId") Long recordId) {
        HealthHistoryVO healthHistoryVO = new HealthHistoryVO();

        ZsPatientRecord patientRecord = this.patientRecordService.selectByPrimaryKey(recordId);
        if (StringUtils.isNullorEmpty(patientRecord)) {
            return ResultUtil.returnError(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        String endTime = patientRecord.getEndTime();
        healthHistoryVO.setStartTime(patientRecord.getStartTime());
        List<HealthHistory> list;
        if (StringUtils.isNullorEmpty(endTime)) {
            String currentTime = DateUtil.getCurrentTime();
            healthHistoryVO.setEndTime(currentTime).setUseTimes(DateUtil.timeDifferent(patientRecord.getStartTime()));
            healthHistoryVO.setUseTimes(DateUtil.timeDifferent(patientRecord.getStartTime(), currentTime));
            list = this.ecgService.getHealthHistory(recordId);
        } else {
            list = JSON.parseArray(patientRecord.getRateHistory(), HealthHistory.class);
            healthHistoryVO.setList(list);
            healthHistoryVO.setEndTime(patientRecord.getEndTime());
            healthHistoryVO.setUseTimes(DateUtil.timeDifferent(patientRecord.getStartTime(), patientRecord.getEndTime()));
            return ResultUtil.returnSuccess(healthHistoryVO);
        }
        healthHistoryVO = this.ecgService.parseRateHistory(list, healthHistoryVO);
        return ResultUtil.returnSuccess(healthHistoryVO);
    }

    @Deprecated
    @ApiOperation("心电历史记录")
    @RequestMapping(value = {"/ecgHistory"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public CommonResult<HealthHistoryVO> getEcgHistory(@RequestParam("recordId") Long recordId, @RequestParam("timestamp") Long timestamp) {
        HealthHistoryVO healthHistoryVO = new HealthHistoryVO();

        ZsPatientRecord patientRecord = this.patientRecordService.selectByPrimaryKey(recordId);
        if (StringUtils.isNullorEmpty(patientRecord)) {
            return ResultUtil.returnError(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        String endTime = patientRecord.getEndTime();
        healthHistoryVO.setStartTime(patientRecord.getStartTime());
        List<HealthHistory> list;
        if (StringUtils.isNullorEmpty(endTime)) {
            String currentTime = DateUtil.getCurrentTime();
            healthHistoryVO.setEndTime(currentTime).setUseTimes(DateUtil.timeDifferent(patientRecord.getStartTime()));
            healthHistoryVO.setUseTimes(DateUtil.timeDifferent(patientRecord.getStartTime(), currentTime));
            list = this.ecgService.getHealthHistory(recordId);
        } else {
            list = JSON.parseArray(patientRecord.getRateHistory(), HealthHistory.class);
            healthHistoryVO.setList(list);
            healthHistoryVO.setEndTime(patientRecord.getEndTime());
            healthHistoryVO.setUseTimes(DateUtil.timeDifferent(patientRecord.getStartTime(), patientRecord.getEndTime()));
            return ResultUtil.returnSuccess(healthHistoryVO);
        }
        healthHistoryVO = this.ecgService.parseRateHistory(list, healthHistoryVO);
        return ResultUtil.returnSuccess(healthHistoryVO);
    }

    /**
     * @Description 
     * @param recordId
     * @return com.yjjk.monitor.configer.CommonResult 
     */
    @ApiOperation("停止监测")
    @RequestMapping(value = {"/stopRecord"}, method = {org.springframework.web.bind.annotation.RequestMethod.PUT})
    public CommonResult stopEcgMonitor(@ApiParam(value = "记录id", required = true) @RequestParam("recordId") Long recordId) throws Exception {
        ZsPatientRecord patientRecord = this.patientRecordService.selectByPrimaryKey(recordId);
        if (StringUtils.isNullorEmpty(patientRecord)) {
            return ResultUtil.returnError(ErrorCodeEnum.NON_RECORD);
        }
        if ((!patientRecord.getRecordType().equals(PatientRecordConstant.TYPE_HEALTH)) || (patientRecord.getUsageState().equals(PatientRecordConstant.USAGE_STATE_UNUSED))) {
            return ResultUtil.returnError(ErrorCodeEnum.ERROR_RECORD);
        }
        boolean b = this.ecgService.stopEcg(patientRecord);
        if (!b) {
            return ResultUtil.returnError(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        BackgroundResult backgroundResult = super.ecgService.connectEcgMachine(patientRecord.getMachineId(), patientRecord.getBedId(), BackgroundSend.DATA_LOSE_CONNECTION);
        if ("false".equals(backgroundResult.getSuccess())){
            return ResultUtil.returnError(backgroundResult.getCode(),backgroundResult.getMessage());
        }
        ZsMachineInfo machineInfo = new ZsMachineInfo();
        machineInfo.setMachineId(patientRecord.getMachineId()).setUsageState(MachineConstant.USAGE_STATE_USED);
        super.machineService.updateByMachineId(machineInfo);
        return ResultUtil.returnSuccess(Boolean.valueOf(b));
    }


    @ApiOperation("心电设备记录信息导出")
    @RequestMapping(value = {"/export"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public void exportTemperatureHistory(@RequestParam("timeList") List<String> timeList, @RequestParam("token") String token,
                                         HttpServletResponse response)
            throws IOException {
        if (timeList.size() == 0) {
            return;
        }
        Map<String, Object> paraMap = new HashMap();
        List<HealthRecordHistory2Excel> list = new ArrayList();
        for (int i = 0; i < timeList.size(); i++) {
            paraMap.put("time", timeList.get(i));
            paraMap.put("threshold", DateUtil.getTwoMinutePast((String) timeList.get(i)));
            paraMap.put("token", token);
            list.addAll(this.patientRecordService.getHealthExportList(paraMap));
            paraMap.clear();
        }
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("sheet1");

        HSSFRow row = null;

        row = sheet.createRow(0);
        row.setHeight((short) 525);

        row.createCell(0).setCellValue("心电监测平台使用日志");

        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 7);
        sheet.addMergedRegion(rowRegion);

        row = sheet.createRow(1);

        row.setHeight((short) 450);

        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("住院号");
        row.createCell(2).setCellValue("科室");
        row.createCell(3).setCellValue("房号");
        row.createCell(4).setCellValue("床位");
        row.createCell(5).setCellValue("时间点");
        row.createCell(6).setCellValue("心率");
        row.createCell(7).setCellValue("呼吸率");
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 2);
            HealthRecordHistory2Excel record = (HealthRecordHistory2Excel) list.get(i);
            row.createCell(0).setCellValue(record.getPatientName());
            row.createCell(1).setCellValue(record.getCaseNum());
            row.createCell(2).setCellValue(record.getDepartmentName());
            row.createCell(3).setCellValue(record.getRoom());
            row.createCell(4).setCellValue(record.getBed());
            row.createCell(5).setCellValue(record.getTime());
            row.createCell(6).setCellValue(record.getHeartRate());
            row.createCell(7).setCellValue(record.getRespiratoryRate());
        }
        sheet.setDefaultRowHeight((short) 330);
        for (int i = 0; i <= 13; i++) {
            sheet.autoSizeColumn(i);
        }
        response.setContentType("application/octet-stream;charset=utf-8");
        OutputStream os = response.getOutputStream();

        response.setHeader("Content-disposition", "attachment;filename=" + DateUtil.getHealthHistoryFileName() + ".xls");
        wb.write(os);
        wb.close();
    }

}
