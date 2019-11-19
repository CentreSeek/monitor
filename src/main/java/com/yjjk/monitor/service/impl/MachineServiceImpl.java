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
import com.yjjk.monitor.entity.ZsMachineTypeInfo;
import com.yjjk.monitor.entity.export.MachineExport;
import com.yjjk.monitor.entity.export.MachineExportVO;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.MachineService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CentreS
 * @Description: 设备管理
 * @create 2019/7/18
 */
@Service
public class MachineServiceImpl extends BaseService implements MachineService {
    @Override
    public int insertSelective(ZsMachineInfo machineInfo)
    {
        return this.ZsMachineInfoMapper.insertSelective(machineInfo);
    }
    @Override
    public int deleteMachine(Integer machineId, String remark)
    {
        ZsMachineInfo machineInfo = new ZsMachineInfo();
        machineInfo.setMachineId(machineId);
        machineInfo.setRemark(remark);
        machineInfo.setUsageState(Integer.valueOf(1));
        return this.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
    }
    @Override
    public int insertByMachineNum(ZsMachineInfo machineInfo)
    {
        return this.ZsMachineInfoMapper.insertByMachineNum(machineInfo);
    }
    @Override
    public int selectCount(ZsMachineInfo machineInfo)
    {
        return this.ZsMachineInfoMapper.selectCount(machineInfo);
    }
    @Override
    public List<ZsMachineInfo> selectByUsageState(ZsMachineInfo machineInfo)
    {
        return this.ZsMachineInfoMapper.selectByUsageState(machineInfo);
    }
    @Override
    public List<MachineExportVO> export(ZsMachineInfo machineInfo)
    {
        List<MachineExport> list = this.ZsMachineInfoMapper.export(machineInfo);
        List<MachineExportVO> reqList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            reqList.add(((MachineExport)list.get(i)).transBean((MachineExport)list.get(i)));
        }
        return reqList;
    }
    @Override
    public int updateByMachineId(ZsMachineInfo machineInfo)
    {
        return this.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
    }
    @Override
    public ZsMachineInfo selectByPrimaryKey(Integer machineId)
    {
        return this.ZsMachineInfoMapper.selectByPrimaryKey(machineId);
    }
    @Override
    public int selectByMachineNum(String machineNum)
    {
        return this.ZsMachineInfoMapper.selectByMachineNum(machineNum);
    }
    @Override
    public int selectByMachineNo(String machineNo)
    {
        return this.ZsMachineInfoMapper.selectCountByMachineNo(machineNo);
    }
    @Override
    public List<ZsMachineTypeInfo> selectMachineTypes()
    {
        return this.zsMachineTypeInfoMapper.selectMachineTypes();
    }
    @Override
    public List<ZsMachineTypeInfo> selectMachineNums(Integer id)
    {
        return this.zsMachineTypeInfoMapper.selectMachineNums(id);
    }
    @Override
    public List<ZsMachineInfo> selectTemperatureMachines(Integer departmentId)
    {
        return this.ZsMachineInfoMapper.selectTemperatureMachines(departmentId);
    }
    @Override
    public List<ZsMachineInfo> selectHeartMachines(Integer departmentId)
    {
        return this.ZsMachineInfoMapper.selectHeartMachines(departmentId);
    }
}
