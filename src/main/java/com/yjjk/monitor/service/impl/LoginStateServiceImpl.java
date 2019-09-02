/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: LoginStateServiceImpl
 * Author:   CentreS
 * Date:     2019/8/4 17:14
 * Description: 单点登录
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service.impl;

import com.yjjk.monitor.entity.ZsLoginState;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.LoginStateService;
import com.yjjk.monitor.utility.DateUtil;
import com.yjjk.monitor.utility.PasswordUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author CentreS
 * @Description: 单点登录
 * @create 2019/8/4
 */
@Service
public class LoginStateServiceImpl extends BaseService implements LoginStateService {

    @Transactional
    @Override
    public String login(HttpServletRequest request, Integer managerId) {
        ZsLoginState loginState = super.zsLoginStateMapper.selectByManagerId(managerId);
        String token = "";
        int i = 0;
        int j = 0;
        if (loginState != null) {
            i = super.zsLoginStateMapper.updateByPrimaryKeySelective(loginState.setIp(request.getRemoteAddr()));
            token = loginState.getToken();
        } else {
            // 生成登录信息
            loginState = new ZsLoginState();
            token = PasswordUtils.salt();
            loginState.setToken(token);
            loginState.setIp(request.getRemoteAddr());
            loginState.setManagerId(managerId);
            loginState.setStatus(0);
            j = super.zsLoginStateMapper.insertSelective(loginState);
        }
        return token;
    }

    @Override
    public int loginOut(String token) {
        ZsLoginState loginState = new ZsLoginState();
        loginState.setToken(token);
        loginState.setStatus(1);
        loginState.setLoginOut(DateUtil.getCurrentTime());
        int i = super.zsLoginStateMapper.updateByPrimaryKeySelective(loginState);
        return i;
    }

    @Override
    public boolean checkLogin(String token, String ip) {
        ZsLoginState loginState = super.zsLoginStateMapper.selectByPrimaryKey(token);
        if (loginState == null) {
            return false;
        } else {
            return ip.equals(loginState.getIp());
        }
    }
}
