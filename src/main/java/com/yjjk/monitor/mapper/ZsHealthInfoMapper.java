package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsHealthInfo;

public interface ZsHealthInfoMapper {
    int deleteByPrimaryKey(Integer temperatureId);

    int insert(ZsHealthInfo record);

    int insertSelective(ZsHealthInfo record);

    ZsHealthInfo selectByPrimaryKey(Integer temperatureId);

    int updateByPrimaryKeySelective(ZsHealthInfo record);

    int updateByPrimaryKey(ZsHealthInfo record);
}