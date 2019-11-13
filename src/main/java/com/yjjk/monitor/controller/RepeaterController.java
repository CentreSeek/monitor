/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: RepeaterController
 * Author:   CentreS
 * Date:     2019/8/23 9:49
 * Description: 中继器管理模块
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.controller;

import com.yjjk.monitor.entity.ZsMachineTypeInfo;
import com.yjjk.monitor.entity.ZsRepeaterInfo;
import com.yjjk.monitor.utility.DateUtil;
import com.yjjk.monitor.utility.StringUtils;
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
 * @Description: 中继器管理模块
 * @create 2019/8/23
 */
@RestController
@RequestMapping("repeater")
public class RepeaterController extends BaseController {

    /**
     * 查询设备信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/machineType", method = RequestMethod.GET)
    public void getMachineType(HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";

        List<ZsMachineTypeInfo> list = super.repeaterService.selectMachineTypes();
        message = "信息查询成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, list == null ? "" : list);
    }

    /**
     * 查询设备型号
     *
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "/machineNum", method = RequestMethod.GET)
    public void getRepeaterNum(@RequestParam(value = "id") Integer id,
                              HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";

        List<ZsMachineTypeInfo> list = super.repeaterService.selectMachineNums(id);
        message = "信息查询成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, list == null ? "" : list);
    }

    @RequestMapping(value = "/repeater", method = RequestMethod.POST)
    public void addRepeater(@RequestParam(value = "machineTypeId") Integer machineTypeId,
                            @RequestParam(value = "mac") String mac,
                            @RequestParam(value = "departmentId") Integer departmentId,
                            @RequestParam(value = "roomId") Integer roomId,
                            @RequestParam(value = "ip") String ip,
                            HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";


        int i = super.repeaterService.insertSelective(new ZsRepeaterInfo().setMachineTypeId(machineTypeId).setMac(mac).setDepartmentId(departmentId).setRoomId(roomId).setIp(ip));
        message = "新增成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, i);
    }

    /**
     * 启用路由器
     *
     * @param zsRepeaterInfo
     * @param request
     * @param response
     */
    @RequestMapping(value = "/start", method = RequestMethod.PUT)
    public void startRepeater(ZsRepeaterInfo zsRepeaterInfo,
                              HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";
        if (StringUtils.isNullorEmpty(zsRepeaterInfo) || StringUtils.isNullorEmpty(zsRepeaterInfo.getId())) {
            message = "参数错误";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }

        int i = super.repeaterService.startRepeater(zsRepeaterInfo);
        message = "修改成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, i);
    }


    /**
     * 停用路由器
     *
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "/stop", method = RequestMethod.PUT)
    public void stopRepeater(@RequestParam(value = "id") Integer id,
                             @RequestParam(value = "remark") String remark,
                             HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";


        int i = super.repeaterService.stopRepeater(id, DateUtil.getCurrentTime() + remark);
        message = "修改成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, i);
    }

    @RequestMapping(value = "/repeater", method = RequestMethod.GET)
    public void getRepeaters(@RequestParam(value = "departmentId", required = false) Integer departmentId,
                             @RequestParam(value = "currentPage", required = false) Integer currentPage,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize,
                             HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";
        Map<String, Object> map = new HashMap<>();
        ZsRepeaterInfo repeaterInfo = new ZsRepeaterInfo();

        /********************** 分页 **********************/
        repeaterInfo.setDepartmentId(departmentId);
        if (!StringUtils.isNullorEmpty(currentPage) && !StringUtils.isNullorEmpty(pageSize)) {
            if (currentPage <= 0 || pageSize <= 0) {
                message = "页码出错";
                returnResult(startTime, request, response, resultCode, message, "");
                return;
            }
            // 查询总条数
            int totalCount = super.repeaterService.selectRepeaterCount(repeaterInfo);
            // 分页必须信息
            int startLine = (currentPage - 1) * (pageSize);
            repeaterInfo.setStartLine(startLine).setPageSize(pageSize);
            map.put("totalCount", totalCount);
            map.put("currentPage", currentPage);
        }

        List<ZsRepeaterInfo> list = super.repeaterService.selectRepeaters(repeaterInfo);
        map.put("list", list);
        message = "查询成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, map);
    }

}
