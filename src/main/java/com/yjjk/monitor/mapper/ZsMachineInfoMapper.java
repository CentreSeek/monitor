package com.yjjk.monitor.mapper;


import com.yjjk.monitor.entity.ZsMachineInfo;
import com.yjjk.monitor.entity.export.MachineExport;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZsMachineInfoMapper {
    int deleteByPrimaryKey(Integer machineId);

    int insert(ZsMachineInfo record);

    int insertSelective(ZsMachineInfo record);

    ZsMachineInfo selectByPrimaryKey(Integer machineId);

    int updateByPrimaryKeySelective(ZsMachineInfo record);

    int updateByPrimaryKey(ZsMachineInfo record);

    /**
     * insert---批量插入设备
     * @param machineInfo
     * @return
     */
    int insertByMachineNum(ZsMachineInfo machineInfo);

    /**
     * select---查询设备总数(machineId, usageState)
     * @param machineInfo
     * @return
     */
    int selectCount(ZsMachineInfo machineInfo);

    /**
     * select---分页查询设备信息(usageSate)
     * @param machineInfo
     * @return
     */
    List<ZsMachineInfo> selectByUsageState(ZsMachineInfo machineInfo);

    /**
     * 设备导出
     * @param machineInfo
     * @return
     */
    List<MachineExport> export(ZsMachineInfo machineInfo);

    /**
     * 使用SN编号查询设备数量
     * @param machineNum
     * @return
     */
    int selectByMachineNum(String machineNum);

    /**
     * 使用设备编号查询设备数量
     * @param machineNum
     * @return
     */
    int selectCountByMachineNo(String machineNum);

    /**
     * 查询体温设备编号列表
     * @param departmentId
     * @return
     */
    List<ZsMachineInfo> selectTemperatureMachines(Integer departmentId);

    /**
     * 查询心电设备编号列表
     * @param departmentId
     * @return
     */
    List<ZsMachineInfo> selectHeartMachines(Integer departmentId);


}