/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: ManagerController
 * Author:   CentreS
 * Date:     2019/7/17 13:58
 * Description: 管理员模块
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.controller;

import com.yjjk.monitor.entity.*;
import com.yjjk.monitor.utility.PasswordUtils;
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
 * @Description: 管理员模块
 * @create 2019/7/17
 */
@RestController
@RequestMapping("/manage")
public class ManagerController extends BaseController {

    /**
     * 新增管理员
     *
     * @param managerInfo
     * @param request
     * @param response
     */
    @RequestMapping(value = "/manager", method = RequestMethod.POST)
    public void addManager(ZsManagerInfo managerInfo, HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";
        if (StringUtils.isNullorEmpty(managerInfo.getAccount()) || StringUtils.isNullorEmpty(managerInfo.getPassword())
                || StringUtils.isNullorEmpty(managerInfo.getName()) || StringUtils.isNullorEmpty(managerInfo.getPhone())
                || StringUtils.isNullorEmpty(managerInfo.getSex()) || StringUtils.isNullorEmpty(managerInfo.getDepartmentId())) {
            message = "参数错误";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        // 密码加密
        managerInfo.setPassword(PasswordUtils.generate(managerInfo.getPassword()));
        managerInfo.setRole(2);
        managerInfo.setStatus(0);
        int i = super.managerService.insertManager(managerInfo);
        if (i == 0) {
            message = "新增失败";
            returnResult(startTime, request, response, resultCode, message, i);
            return;
        }
        message = "新增成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, i);
    }

    /**
     * 更新管理员信息
     *
     * @param managerInfo
     * @param request
     * @param response
     */
    @RequestMapping(value = "/manager", method = RequestMethod.PUT)
    public void updateManager(ZsManagerInfo managerInfo, HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";
        if (StringUtils.isNullorEmpty(managerInfo.getManagerId())) {
            message = "参数错误";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }

        // 密码加密
        managerInfo.setPassword(PasswordUtils.generate(managerInfo.getPassword()));
        int i = super.managerService.updateManger(managerInfo);
        if (i == 0) {
            message = "更新失败";
            returnResult(startTime, request, response, resultCode, message, i);
            return;
        }
        message = "更新成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, i);
    }

    /**
     * 删除管理员
     *
     * @param managerId
     * @param request
     * @param response
     */
    @RequestMapping(value = "/manager", method = RequestMethod.DELETE)
    public void updateManager(@RequestParam(value = "managerId") Integer managerId,
                              HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";
        ZsManagerInfo managerInfo = new ZsManagerInfo();
        managerInfo.setManagerId(managerId);
        managerInfo.setStatus(1);

        int i = super.managerService.updateManger(managerInfo);
        if (i == 0) {
            message = "删除失败";
            returnResult(startTime, request, response, resultCode, message, i);
            return;
        }
        message = "删除成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, i);
    }

    /**
     * 获取管理员信息
     *
     * @param managerId
     * @param request
     * @param response
     */
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public void getManagerInfo(@RequestParam(value = "managerId", required = false) Integer managerId,
                               @RequestParam(value = "currentPage", required = false) Integer currentPage,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize,
                               HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";

        ZsManagerInfo managerInfo = null;
        List<ZsManagerInfo> zsManagerInfos = null;
        Map<String, Object> paramMap = new HashMap<>();
        Map<String, Object> reqMap = new HashMap<>();
        if (StringUtils.isNullorEmpty(managerId)) {
            int totalCount = super.managerService.selectNormalListCount(paramMap);
            int startLine = (currentPage - 1) * (pageSize);
            int totalPage = (totalCount + pageSize - 1) / pageSize;
            paramMap.put("startLine", startLine);
            paramMap.put("pageSize", pageSize);
            reqMap.put("totalPage", totalPage);
            reqMap.put("currentPage", currentPage);
            zsManagerInfos = super.managerService.selectNormalList(paramMap);
        } else {
            managerInfo = super.managerService.getManagerInfo(managerId);
        }
        if (StringUtils.isNullorEmpty(managerInfo) && StringUtils.isNullorEmpty(zsManagerInfos)) {
            message = "获取失败";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        reqMap.put("list", managerInfo == null ? managerId : zsManagerInfos);
        message = "获取成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, reqMap);
    }


    /**
     * 管理员登录
     *
     * @param account
     * @param password
     * @param request
     * @param response
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void managerLogin(@RequestParam(value = "account") String account,
                             @RequestParam(value = "password") String password,
                             HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";
        ZsManagerInfo param = new ZsManagerInfo();
        param.setAccount(account);

        ZsManagerInfo managerInfo = super.managerService.selectByAccount(param);
        if (managerInfo == null) {
            message = "账户不存在";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        boolean login = super.managerService.login(param.setPassword(password), managerInfo.getPassword());
        if (login) {
            message = "密码错误";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        message = "登录成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, managerInfo.setPassword(null));
    }

    /**
     * 权限验证
     *
     * @param managerId
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "/access", method = RequestMethod.GET)
    public void managerLogin(@RequestParam(value = "managerId") Integer managerId,
                             @RequestParam(value = "model") Integer model,
                             HttpServletRequest request, HttpServletResponse response) {
        /********************** 参数初始化 **********************/
        long startTime = System.currentTimeMillis();
        boolean resultCode = false;
        String message = "";

        ZsManagerInfo managerInfo = super.managerService.getManagerInfo(managerId);
        if (managerInfo == null) {
            message = "账户不存在";
            returnResult(startTime, request, response, resultCode, message, "");
            return;
        }
        switch (managerInfo.getRole()) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                if (!model.equals(PLATEFORM) && !model.equals(HISTORY_RECORD)) {
                    message = "权限验证失败";
                    returnResult(startTime, request, response, resultCode, message, "");
                    return;
                }
        }
        message = "验证成功";
        resultCode = true;
        returnResult(startTime, request, response, resultCode, message, managerInfo.setPassword(null));
    }
}
