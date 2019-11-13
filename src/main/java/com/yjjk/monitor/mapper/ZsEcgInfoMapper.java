package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsEcgInfo;

public interface ZsEcgInfoMapper {
    int deleteByPrimaryKey(Integer temperatureId);

    int insert(ZsEcgInfo record);

    int insertSelective(ZsEcgInfo record);

    ZsEcgInfo selectByPrimaryKey(Integer temperatureId);

    int updateByPrimaryKeySelective(ZsEcgInfo record);

    int updateByPrimaryKeyWithBLOBs(ZsEcgInfo record);

    int updateByPrimaryKey(ZsEcgInfo record);
}