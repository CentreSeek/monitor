package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsTemperatureInfo;

public interface ZsTemperatureInfoMapper {
    int deleteByPrimaryKey(Integer temperatureId);

    int insert(ZsTemperatureInfo record);

    int insertSelective(ZsTemperatureInfo record);

    ZsTemperatureInfo selectByPrimaryKey(Integer temperatureId);

    int updateByPrimaryKeySelective(ZsTemperatureInfo record);

    int updateByPrimaryKey(ZsTemperatureInfo record);

    /**
     * delete---定期清理temperatureInfo表数据
     * @param dateOfOneMonthAgo
     * @return
     */
    int temperatureInfoTask(String dateOfOneMonthAgo);
}