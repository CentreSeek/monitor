package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsHealthInfo;

import java.util.List;

public interface ZsHealthInfoMapper {
    int deleteByPrimaryKey(Integer temperatureId);

    int insert(ZsHealthInfo record);

    int insertSelective(ZsHealthInfo record);

    ZsHealthInfo selectByPrimaryKey(Integer temperatureId);

    int updateByPrimaryKeySelective(ZsHealthInfo record);

    int updateByPrimaryKey(ZsHealthInfo record);

    /**
     * 获取最新心率、呼吸率信息
     * @param departmentId
     * @return
     */
    List<ZsHealthInfo> getHealthInfo(Integer departmentId);
}