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

import com.yjjk.monitor.entity.ZsDepartmentInfo;
import com.yjjk.monitor.entity.ZsMachineInfo;
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
    public void addMachine(@RequestParam(value = "departmentId", required = false) Integer departmentId,
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
}
