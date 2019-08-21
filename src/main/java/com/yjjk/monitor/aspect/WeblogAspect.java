/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: WeblogAspect
 * Author:   CentreS
 * Date:     2019/6/27 15:23
 * Description: 纪录日志
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.aspect;

/**
 * @Description: 纪录日志
 * @author CentreS
 * @create 2019/6/27
 */

import com.yjjk.monitor.service.LoginStateService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class WeblogAspect {

    final static Logger logger = LoggerFactory.getLogger(WeblogAspect.class);

    @Resource
    LoginStateService loginStateService;

    public WeblogAspect() {

    }

    @Pointcut("execution(public * com.yjjk.monitor.controller.*.*(..))")
    private void controllerAspect() {

    }

    @Before(value = "controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        logger.info("[ip:{}] [url:{}] [method:({}) {}]", request.getRemoteAddr(), request.getRequestURI(),
                request.getMethod(), joinPoint.getSignature());
        logger.info("args: " + Arrays.toString(joinPoint.getArgs()));
    }

    @Around(value = "controllerAspect()")
    public synchronized Object loginCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getParameter("token");
        Signature signature = joinPoint.getSignature();
        if (!signature.getName().equals("managerLogin") && !signature.getName().equals("managerLoginOut")) {
            if (token == null) {
                logger.error("登录失败：  token为空");
                return "登录失败：  token为空";
            } else {
                boolean check = loginStateService.checkLogin(token, request.getRemoteAddr());
                if (!check) {
                    logger.error("登录失败：  token = " + token);
                    return "登录失败：  token = " + token;
                }
            }
        }
        return joinPoint.proceed();
    }
}

