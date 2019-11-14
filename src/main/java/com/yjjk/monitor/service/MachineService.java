/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: MachineService
 * Author:   CentreS
 * Date:     2019/7/18 13:49
 * Description: 设备管理
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service;

import com.yjjk.monitor.entity.ZsMachineInfo;
import com.yjjk.monitor.entity.ZsMachineTypeInfo;
import com.yjjk.monitor.entity.export.MachineExportVO;

import java.util.List;

/**
 * @Description: 设备管理
 * @author CentreS
 * @create 2019/7/18
 */
public interface MachineService {

    /**
     * 新增设备
     * @param machineInfo
     * @return
     */
    int insertSelective(ZsMachineInfo machineInfo);

    /**
     * 删除设备
     * @param machineId
     * @param remark
     * @return
     */
    int deleteMachine(Integer machineId, String remark);

    /**
     * 批量插入设备
     * @param machineInfo
     * @return
     */
    int insertByMachineNum(ZsMachineInfo machineInfo);

    /**
     * 查询设备总数(machineId, usageState)
     * @param machineInfo
     * @return
     */
    int selectCount(ZsMachineInfo machineInfo);

    /**
     * 根据设备的使用状态查询
     * @param machineInfo
     * @return
     */
    List<ZsMachineInfo> selectByUsageState(ZsMachineInfo machineInfo);

    /**
     * 设备导出
     * @param machineInfo
     * @return
     */
    List<MachineExportVO> export(ZsMachineInfo machineInfo);

    /**
     * 更新设备信息
     * @param machineInfo
     * @return
     */
    int updateByMachineId(ZsMachineInfo machineInfo);

    /**
     * 查找设备信息
     * @param machineId
     * @return
     */
    ZsMachineInfo selectByPrimaryKey(Integer machineId);

    /**
     * 使用SN编号查询设备数量
     * @param machineNum
     * @return
     */
    int selectByMachineNum(String machineNum);

    /**
     * 使用设备编号查询设备数量
     * @param machineNo
     * @return
     */
    int selectByMachineNo(String machineNo);

    /**
     * 查询设备名称
     * @return
     */
    List<ZsMachineTypeInfo> selectMachineTypes();

    /**
     * 查询设备型号
     * @param id
     * @return
     */
    List<ZsMachineTypeInfo> selectMachineNums(Integer id);

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
