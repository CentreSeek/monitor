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
import com.yjjk.monitor.entity.ZsManagerInfo;
import com.yjjk.monitor.entity.ZsPatientInfo;
import com.yjjk.monitor.entity.ZsPatientRecord;
import com.yjjk.monitor.entity.json.TemperatureHistory;
import com.yjjk.monitor.entity.vo.PatientTemperature;
import com.yjjk.monitor.entity.vo.RecordHistory;
import com.yjjk.monitor.entity.vo.UseMachine;
import com.yjjk.monitor.utility.DateUtil;
import com.yjjk.monitor.utility.StringUtils;
import org.springframework.beans.AbstractPropertyAccessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CentreS
 * @Description: 病人管理模块
 * @create 2019/7/19
 */
@RestController
@RequestMapping("patient")
public class PatientController extends BaseController {

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
    @RequestMapping(value = "/patient", method = RequestMethod.POST)
    public void addMachine(@RequestParam(value = "bedId") Integer bedId,
                           @RequestParam(value = "machineId") Integer machineId,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "caseNum") String caseNum,
                           @RequestParam(value = "managerId") Integer managerId,
                           HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";
        int patientCount = patientRecordService.selectByBedId(bedId);
        if (patientCount > 0){
            message = "该病床已绑定病人";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        ZsManagerInfo managerInfo = super.managerService.getManagerInfo(managerId);

        ZsPatientInfo zsPatientInfo = super.patientService.addPatient(name, caseNum, bedId, managerInfo.getDepartmentId());
        if (StringUtils.isNullorEmpty(zsPatientInfo)) {
            message = "新增病人信息失败";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        ZsPatientRecord patientRecord = new ZsPatientRecord();
        patientRecord.setStartTime(DateUtil.getCurrentTime()).setMachineId(machineId).setPatientId(zsPatientInfo.getPatientId());
        int i = super.patientRecordService.addPatientRecord(patientRecord);
        if (i == 0) {
            message = "新增使用信息失败";
            returnResult(startTime, request, response, resultCode, message, "");
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

        ZsPatientRecord patientRecord = super.patientRecordService.selectByPrimaryKey(recordId);
        if (StringUtils.isNullorEmpty(patientRecord)) {
            message = "未找到该记录";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }

        int i = super.patientRecordService.stopMonitoring(patientRecord.getPatientId(), patientRecord.getMachineId());
        if (i == 0) {
            message = "停用失败";
            returnResult(startTime, request, response, resultCode, message, i);
            return;
        }
        message = "停用成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, i);
    }

    /**
     * 获取监控基础信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public void getMinitors(@RequestParam(value = "managerId") Integer managerId,
                            HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";

        ZsManagerInfo managerInfo = super.managerService.getManagerInfo(managerId);
        Integer departmentId = null;
        if (managerInfo.getRole() == 2) {
            departmentId = managerInfo.getDepartmentId();
        }
        List<UseMachine> monitorsInfo = super.patientRecordService.getMonitorsInfo(departmentId);
        message = "查询成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, monitorsInfo == null ? "" : monitorsInfo);
    }

    /**
     * 获取实时监控信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public void getCurrentInfo(@RequestParam(value = "managerId") Integer managerId,
                               HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";

        ZsManagerInfo managerInfo = super.managerService.getManagerInfo(managerId);
        if (managerInfo == null){
            message = "未查询到管理员个人信息";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        Integer departmentId = null;
        if (managerInfo.getRole() == 2) {
            departmentId = managerInfo.getDepartmentId();
        }
        List<PatientTemperature> minitorsTemperature = super.patientRecordService.getMinitorsTemperature(departmentId);
        if (StringUtils.isNullorEmpty(minitorsTemperature)) {
            message = "更新失败";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        message = "更新成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, minitorsTemperature);
    }

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
        Map<String, Object> map = new HashMap<>();

        if (!StringUtils.isNullorEmpty(recordHistory.getCurrentPage()) && !StringUtils.isNullorEmpty(recordHistory.getPageSize())) {
            int currentPage = recordHistory.getCurrentPage();
            int pageSize = recordHistory.getPageSize();
            // 查询总条数
            int totalCount = super.patientRecordService.getRecordHistoryCount(recordHistory);
            // 分页必须信息
            int startLine = (currentPage - 1) * (pageSize);
            // 计算总页数
            int totalPage = (totalCount + pageSize - 1) / pageSize;
            recordHistory.setStartLine(startLine);
            map.put("totalPage", totalPage);
            map.put("currentPage", currentPage);
        }

        List<RecordHistory> list = super.patientRecordService.getRecordHistory(recordHistory);
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
        Map<String, Object> reqMap = new HashMap<>(2);
        Map<String, Object> paraMap = new HashMap<>();

        ZsPatientRecord patientRecord = super.patientRecordService.selectByPrimaryKey(recordId);
        if (StringUtils.isNullorEmpty(patientRecord)) {
            message = "未找到该记录信息";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        String endTime = patientRecord.getEndTime();
        if (StringUtils.isNullorEmpty(endTime)){
            paraMap.put("endTime", DateUtil.getCurrentTime());
        }else {
            paraMap.put("endTime", endTime);
        }
        paraMap.put("patientId", patientRecord.getPatientId());

        List<TemperatureHistory> list = super.patientRecordService.getCurrentTemperatureRecord(paraMap);
        if (StringUtils.isNullorEmpty(patientRecord.getTemperatureHistory())) {
            reqMap.put("useTimes", DateUtil.getDatePoor(patientRecord.getStartTime()));
        } else {
            list = JSON.parseArray(patientRecord.getTemperatureHistory(), TemperatureHistory.class);
            reqMap.put("useTimes", DateUtil.getDatePoor(patientRecord.getStartTime(), patientRecord.getEndTime()));
        }
        reqMap.put("list", list);
        message = "查询成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, reqMap);
    }
}
