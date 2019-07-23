/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: MachineServiceImpl
 * Author:   CentreS
 * Date:     2019/7/18 13:49
 * Description: 设备管理
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service.impl;

import com.yjjk.monitor.entity.ZsMachineInfo;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.MachineService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CentreS
 * @Description: 设备管理
 * @create 2019/7/18
 */
@Service
public class MachineServiceImpl extends BaseService implements MachineService {

    @Override
    public int insertSelective(ZsMachineInfo machineInfo) {
        return super.ZsMachineInfoMapper.insertSelective(machineInfo);
    }

    @Override
    public int deleteMachine(Integer machineId, String remark) {
        ZsMachineInfo machineInfo = new ZsMachineInfo();
        machineInfo.setMachineId(machineId);
        machineInfo.setRemark(remark);
        machineInfo.setUsageState(1);
        return super.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
    }

    @Override
    public int insertByMachineNums(ZsMachineInfo machineInfo) {
        return super.ZsMachineInfoMapper.insertByMachineNums(machineInfo);
    }

    @Override
    public int selectCount(Integer usageState) {
        ZsMachineInfo machineInfo = new ZsMachineInfo();
        machineInfo.setUsageState(usageState);
        return super.ZsMachineInfoMapper.selectCount(machineInfo);
    }

    @Override
    public List<ZsMachineInfo> selectByUsageState(ZsMachineInfo machineInfo) {
        return super.ZsMachineInfoMapper.selectByUsageState(machineInfo);
    }

    @Override
    public int updateByMachineId(ZsMachineInfo machineInfo) {
        return super.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
    }
}
