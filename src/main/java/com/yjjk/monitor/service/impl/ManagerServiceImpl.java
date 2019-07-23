/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: ManagerServiceImpl
 * Author:   CentreS
 * Date:     2019/7/17 14:12
 * Description: 管理员服务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service.impl;

import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.ManagerService;
import com.yjjk.monitor.entity.*;
import com.yjjk.monitor.utility.PasswordUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CentreS
 * @Description: 管理员服务
 * @create 2019/7/17
 */
@Service
public class ManagerServiceImpl extends BaseService implements ManagerService {

    @Override
    public int insertManager(ZsManagerInfo managerInfo) {
        return super.ZsManagerInfoMapper.insert(managerInfo);
    }

    @Override
    public int updateManger(ZsManagerInfo managerInfo) {
        return super.ZsManagerInfoMapper.updateByPrimaryKeySelective(managerInfo);
    }

    @Override
    public ZsManagerInfo getManagerInfo(Integer managerId) {
        return super.ZsManagerInfoMapper.selectByPrimaryKey(managerId);
    }

    @Override
    public List<ZsManagerInfo> selectNormalList() {
        return super.ZsManagerInfoMapper.selectNormalList();
    }

    @Override
    public boolean login(ZsManagerInfo managerInfo, String secret) {
        return PasswordUtils.verify(managerInfo.getPassword(), secret);
    }

    @Override
    public ZsManagerInfo selectByAccount(ZsManagerInfo managerInfo) {
        return super.ZsManagerInfoMapper.selectByAccount(managerInfo);
    }

}
