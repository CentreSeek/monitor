/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: MachineController
 * Author:   CentreS
 * Date:     2019/7/18 11:34
 * Description: 设备管理
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.controller;

import com.yjjk.monitor.entity.ZsMachineInfo;
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
 * @Description: 设备管理
 * @create 2019/7/18
 */
@RestController
@RequestMapping("/machine")
public class MachineController extends BaseController {

    /**
     * 新增设备
     *
     * @param machineInfo
     * @param request
     * @param response
     */
    @RequestMapping(value = "/machine", method = RequestMethod.POST)
    public void addMachine(ZsMachineInfo machineInfo, HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";
        if (StringUtils.isNullorEmpty(machineInfo.getName()) || StringUtils.isNullorEmpty(machineInfo.getMachineModel()) ||
                StringUtils.isNullorEmpty(machineInfo.getMachineNums()) || StringUtils.isNullorEmpty(machineInfo.getDepartmentId())) {
            message = "参数错误";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        List<String> list = machineInfo.getMachineNums();
        for (int i = 0; i < list.size(); i++) {
            if (!StringUtils.checkMachineNum(list.get(i))) {
                message = "设备号格式错误或“；”为中文符号  错误示例：710/B34.00066041 正确示例：B34/00066041";
                returnResult(startTime, request, response, resultCode, message, "");
                return;
            }
            int count = super.machineService.selectByMachineNum(list.get(i));
            if (count > 0) {
                message = "该设备信息已存在，请核实后录入";
                returnResult(startTime, request, response, resultCode, message, "");
                return;
            }
        }
        int i = super.machineService.insertByMachineNums(machineInfo);
        if (i == 0) {
            message = "设备新增失败";
            returnResult(startTime, request, response, resultCode, message, i);
            return;
        }
        message = "设备新增成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, i);
    }

    /**
     * 停用设备
     *
     * @param machineId
     * @param remark
     * @param request
     * @param response
     */
    @RequestMapping(value = "/machine", method = RequestMethod.DELETE)
    public void updateMachine(@RequestParam(value = "machineId") Integer machineId,
                              @RequestParam(value = "remark", required = false) String remark,
                              HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";
        ZsMachineInfo zsMachineInfo = super.machineService.selectByPrimaryKey(machineId);
        // 使用中设备
        if (zsMachineInfo.getUsageState() == 2) {
            message = "停用失败,设备正在使用中";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        int i = super.machineService.deleteMachine(machineId, remark);
        if (i == 0) {
            message = "设备停用失败";
            returnResult(startTime, request, response, resultCode, message, i);
            return;
        }
        message = "设备停用成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, i);
    }

    /**
     * 获取设备信息
     *
     * @param usageState
     * @param currentPage
     * @param pageSize
     * @param request
     * @param response
     */
    @RequestMapping(value = "/machine", method = RequestMethod.GET)
    public void updateMachine(@RequestParam(value = "usageState", required = false) Integer usageState,
                              @RequestParam(value = "departmentId", required = false) Integer departmentId,
                              @RequestParam(value = "currentPage", required = false) Integer currentPage,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                              HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";
        Map<String, Object> map = new HashMap<>();
        ZsMachineInfo machineInfo = new ZsMachineInfo();
        // 设备检索条件
        if (usageState != null) {
            if (usageState == 0) {
                machineInfo.setUnUsedStatus("unUsed");
            } else if (usageState == 1) {
                machineInfo.setDeleteStatus("delete");
            } else if (usageState == 2) {
                machineInfo.setNormalStatus("normal");
            } else if (usageState > 2) {
                message = "参数错误";
                returnResult(startTime, request, response, resultCode, message, map);
                return;
            }
        }

        machineInfo.setDepartmentId(departmentId);
        if (!StringUtils.isNullorEmpty(currentPage) && !StringUtils.isNullorEmpty(pageSize)) {
            // 查询总条数
            int totalCount = super.machineService.selectCount(machineInfo);
            // 分页必须信息
            int startLine = (currentPage - 1) * (pageSize);
            // 计算总页数
            int totalPage = (totalCount + pageSize - 1) / pageSize;
            machineInfo.setStartLine(startLine);
            machineInfo.setPageSize(pageSize);
            map.put("totalPage", totalPage);
            map.put("currentPage", currentPage);
        }

        List<ZsMachineInfo> list = super.machineService.selectByUsageState(machineInfo);
        map.put("list", list == null ? "" : list);
        message = "查询成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, map);
    }
}
