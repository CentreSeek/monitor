/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: LoginStateService
 * Author:   CentreS
 * Date:     2019/8/4 17:12
 * Description: 单点登录
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service;

import com.yjjk.monitor.entity.ZsLoginState;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 单点登录
 * @author CentreS
 * @create 2019/8/4
 */
public interface LoginStateService {

    /**
     * 新增登录信息
     * @param request
     * @return
     */
    String login(HttpServletRequest request, Integer managerId);

    /**
     * 登出
     * @param token
     * @return
     */
    int loginOut(String token);

    /**
     * 登录检查
     * @param token
     * @param ip
     * @return
     */
    boolean checkLogin(String token, String ip);
}
