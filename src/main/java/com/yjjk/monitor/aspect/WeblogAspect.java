package com.yjjk.monitor.aspect;

import com.yjjk.monitor.constant.ErrorCodeEnum;
import com.yjjk.monitor.service.LoginStateService;
import com.yjjk.monitor.utility.ResultUtil;
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
public class WeblogAspect
{
    static final Logger logger = LoggerFactory.getLogger(WeblogAspect.class);
    @Resource
    LoginStateService loginStateService;

    @Pointcut("execution(public * com.yjjk.monitor.controller.*.*(..))")
    private void controllerAspect() {}

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint)
    {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        logger.info("[ip:{}] [url:{}] [method:({}) {}]", new Object[] { request.getRemoteAddr(), request.getRequestURI(), request
                .getMethod(), joinPoint.getSignature() });
        logger.info("args: " + Arrays.toString(joinPoint.getArgs()));
    }

    @Around("controllerAspect()")
    public synchronized Object loginCheck(ProceedingJoinPoint joinPoint)
            throws Throwable
    {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getParameter("token");
        Signature signature = joinPoint.getSignature();
        if ((!signature.getName().equals("managerLogin")) && (!signature.getName().equals("managerLoginOut")))
        {
            if (token == null)
            {
                logger.error("登录失败  token为空");
                return ResultUtil.returnError(ErrorCodeEnum.TOKEN_ERROR);
            }
            boolean check = this.loginStateService.checkLogin(token, request.getRemoteAddr());
            if (!check)
            {
                logger.error("登录失败  token = " + token);
                return ResultUtil.returnError(ErrorCodeEnum.TOKEN_ERROR);
            }
        }
        return joinPoint.proceed();
    }
}
