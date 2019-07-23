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

import com.yjjk.monitor.entity.ZsDepartmentInfo;
import com.yjjk.monitor.entity.ZsMachineInfo;
import com.yjjk.monitor.entity.ZsPatientInfo;
import com.yjjk.monitor.entity.ZsPatientRecord;
import com.yjjk.monitor.entity.vo.PatientTemperature;
import com.yjjk.monitor.entity.vo.UseMachine;
import com.yjjk.monitor.utility.DateUtil;
import com.yjjk.monitor.utility.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
                           HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";

        ZsPatientInfo zsPatientInfo = super.patientService.addPatient(name, caseNum, bedId);
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
        patientRecord.setMachineId(machineId);
        int i = super.patientRecordService.updateByPrimaryKey(patientRecord);
        if (i == 0) {
            message = "绑定失败";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        message = "绑定成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, "");
    }

    /**
     * 停止检测
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
            message = "获取失败";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }

        patientRecord.setUsageState(1);
        int i = super.patientRecordService.updateByPrimaryKey(patientRecord);
        if (i == 0) {
            message = "停用失败";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        message = "停用成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, "");
    }

    /**
     * 获取监控基础信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "/record", method = RequestMethod.GET)
    public void getMinitors(HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";

        List<UseMachine> monitorsInfo = super.patientRecordService.getMonitorsInfo();
        List<PatientTemperature> minitorsTemperature = super.patientRecordService.getMinitorsTemperature();
        if (StringUtils.isNullorEmpty(monitorsInfo) || StringUtils.isNullorEmpty(minitorsTemperature)) {
            message = "获取失败";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        message = "查询成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, monitorsInfo);
    }

    /**
     * 获取实时监控信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public void getCunnentInfo(HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";

        List<PatientTemperature> minitorsTemperature = super.patientRecordService.getMinitorsTemperature();
        if (StringUtils.isNullorEmpty(minitorsTemperature)) {
            message = "更新失败";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        message = "更新成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, minitorsTemperature);
    }

}
