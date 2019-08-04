/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: BaseController
 * Author:   CentreS
 * Date:     2019-06-18 15:59
 * Description: BaseController
 * History:
 * <author>          <time>          <version>          <desc>
 * CentreS         2019/06/18          1.0.0
 */
package com.yjjk.monitor.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yjjk.monitor.filter.AliValueFilter;
import com.yjjk.monitor.service.*;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CentreS
 * @Description: BaseController
 * @create 2019-06-18
 */
@CrossOrigin
public class BaseController {

    protected static Logger logger = Logger.getLogger(BaseController.class);

    /** 返回值数值 */
    private static String RESULT_CODE_SUCCESS = "200";
    private static String RESULT_CODE_FAIL = "300";

    /** 模块编号 */
    protected static Integer PLATEFORM = 0;
    protected static Integer HISTORY_RECORD = 1;
    protected static Integer BED_MANAGE = 2;
    protected static Integer MACHINE_MANAGE = 3;
    protected static Integer ACCOUNT_MANAGE = 4;


    @Resource
    protected ManagerService managerService;
    @Resource
    protected MachineService machineService;
    @Resource
    protected HospitalService hospitalService;
    @Resource
    protected PatientService patientService;
    @Resource
    protected PatientRecordService patientRecordService;
    @Resource
    protected LoginStateService loginStateService;



    /**
     * WEB端返回值，判断是否过期
     *
     * @param request
     * @param response
     * @param obj
     */
    public void returnResult(long startTime, HttpServletRequest request, HttpServletResponse response,
                             boolean resultCode, String message, Object obj) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("message", message);
            resultMap.put("data", obj);
            //判断是否已经登录
            if (resultCode) {
                resultMap.put("code", RESULT_CODE_SUCCESS);
            } else {
                resultMap.put("code", RESULT_CODE_FAIL);
            }
            //解决long型过长精度丢失的问题
            String jsonArray = JSON.toJSONString(resultMap, new AliValueFilter(), new SerializerFeature[0]);
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            response.setContentType("text/xml;");
            response.setHeader("Cache-Control", "no-cache");
            out.print(jsonArray);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
