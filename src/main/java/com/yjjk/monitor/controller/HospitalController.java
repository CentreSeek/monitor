/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: HospitalController
 * Author:   CentreS
 * Date:     2019/7/18 16:52
 * Description: 医院信息管理
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.controller;

import com.yjjk.monitor.configer.CommonResult;
import com.yjjk.monitor.constant.PatientRecordConstant;
import com.yjjk.monitor.entity.ZsBedInfo;
import com.yjjk.monitor.entity.ZsDepartmentInfo;
import com.yjjk.monitor.entity.ZsManagerInfo;
import com.yjjk.monitor.entity.ZsRoomInfo;
import com.yjjk.monitor.utility.ResultUtil;
import io.swagger.annotations.ApiOperation;
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
 * @Description: 医院信息管理
 * @create 2019/7/18
 */
@RestController
@RequestMapping("/hospital")
public class HospitalController extends BaseController {


    /**
     * 查询科室信息
     * @param departmentId
     * @param request
     * @param response
     */
    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public void selectDetail(@RequestParam(value = "departmentId", required = false) Integer departmentId,
                           HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";


        List<ZsDepartmentInfo> list = super.hospitalService.selectDetail(departmentId);
        message = "信息查询成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, list == null ? "":list);
    }

    /**
     * 查询科室信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    public void getDepartments(HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";


        List<ZsDepartmentInfo> list = super.hospitalService.selectDepartments();
        message = "信息查询成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, list == null ? "":list);
    }

    /**
     * 查询房间信息
     * @param departmentId
     * @param request
     * @param response
     */
    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public void getRooms(@RequestParam(value = "departmentId", required = true) Integer departmentId,
                           HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";


        List<ZsRoomInfo> list = super.hospitalService.selectRooms(departmentId);
        message = "信息查询成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, list == null ? "":list);
    }

    @ApiOperation("体温监测获取空床位")
    @RequestMapping(value={"/temperatureEmptyBeds"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public CommonResult<List<ZsBedInfo>> getTemperatureEmptyBeds(@RequestParam("token") String token)
    {
        ZsManagerInfo managerInfo = this.managerService.selectByToken(token);
        Integer departmentId = managerInfo.getDepartmentId();
        Map<String, Object> paraMap = new HashMap();
        paraMap.put("departmentId", departmentId);
        paraMap.put("recordType", PatientRecordConstant.TYPE_TEMPERATURE);
        List<ZsBedInfo> zsBedInfos = this.hospitalService.selectEmptyBeds(paraMap);

        return ResultUtil.returnSuccess(zsBedInfos);
    }

    @ApiOperation("心电监测获取空床位")
    @RequestMapping(value={"/healthEmptyBeds"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public CommonResult<List<ZsBedInfo>> getHealthEmptyBeds(@RequestParam("token") String token)
    {
        ZsManagerInfo managerInfo = this.managerService.selectByToken(token);
        Integer departmentId = managerInfo.getDepartmentId();
        Map<String, Object> paraMap = new HashMap();
        paraMap.put("departmentId", departmentId);
        paraMap.put("recordType", PatientRecordConstant.TYPE_HEALTH);
        List<ZsBedInfo> zsBedInfos = this.hospitalService.selectEmptyBeds(paraMap);

        return ResultUtil.returnSuccess(zsBedInfos);
    }
}
