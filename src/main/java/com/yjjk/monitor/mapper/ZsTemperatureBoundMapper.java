package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsTemperatureBound;

public interface ZsTemperatureBoundMapper {
    int deleteByPrimaryKey(Integer departmentId);

    int insert(ZsTemperatureBound record);

    int insertSelective(ZsTemperatureBound record);

    ZsTemperatureBound selectByPrimaryKey(Integer departmentId);

    int updateByPrimaryKeySelective(ZsTemperatureBound record);

    int updateByPrimaryKey(ZsTemperatureBound record);
}