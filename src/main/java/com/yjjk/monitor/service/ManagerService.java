/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: ManagerService
 * Author:   CentreS
 * Date:     2019/7/17 14:01
 * Description: 管理员服务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service;

import com.yjjk.monitor.entity.ZsManagerInfo;

import java.util.List;
import java.util.Map;

/**
 * @author CentreS
 * @Description: 管理员服务
 * @create 2019/7/17
 */
public interface ManagerService {

    /**
     * 新增管理员
     *
     * @param managerInfo
     * @return
     */
    int insertManager(ZsManagerInfo managerInfo);

    /**
     * 更新管理员信息
     *
     * @param managerInfo
     * @return
     */
    int updateManger(ZsManagerInfo managerInfo);

    /**
     * 获取管理员信息
     *
     * @param managerId
     * @return
     */
    ZsManagerInfo getManagerInfo(Integer managerId);

    /**
     * 查询所有正常管理员
     *
     * @return
     */
    List<ZsManagerInfo> selectNormalList(Map<String, Object> paramMap);

    /**
     * 查询所有正常管理员
     *
     * @return
     */
    int selectNormalListCount(Map<String, Object> paramMap);

    /**
     * 管理员登录
     *
     * @param managerInfo
     * @return
     */
    boolean login(ZsManagerInfo managerInfo, String secret);

    /**
     * 动态查找管理员信息
     *
     * @param managerInfo
     * @return
     */
    ZsManagerInfo selectByAccount(ZsManagerInfo managerInfo);

    /**
     * 使用token查找管理员信息
     *
     * @param token
     * @return
     */
    ZsManagerInfo selectByToken(String token);
}
