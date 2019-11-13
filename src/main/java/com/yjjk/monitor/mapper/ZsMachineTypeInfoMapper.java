package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsMachineTypeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZsMachineTypeInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsMachineTypeInfo record);

    int insertSelective(ZsMachineTypeInfo record);

    ZsMachineTypeInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsMachineTypeInfo record);

    int updateByPrimaryKey(ZsMachineTypeInfo record);

    /**
     * 查询设备名称（路由器）
     * @return
     */
    List<ZsMachineTypeInfo> selectRepeaterTypes();

    /**
     * 查询设备型号（路由器）
     * @param id
     * @return
     */
    List<ZsMachineTypeInfo> selectRepeaterNums(Integer id);

    /**
     * 查询设备名称（体温贴、心电贴）
     * @return
     */
    List<ZsMachineTypeInfo> selectMachineTypes();

    /**
     * 查询设备型号
     * @param id
     * @return
     */
    List<ZsMachineTypeInfo> selectMachineNums(Integer id);

}