package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsEcgInfo;
import com.yjjk.monitor.entity.json.EcgHistory;

import java.util.List;
import java.util.Map;

public interface ZsEcgInfoMapper {
    int deleteByPrimaryKey(Integer temperatureId);

    int insert(ZsEcgInfo record);

    int insertSelective(ZsEcgInfo record);

    ZsEcgInfo selectByPrimaryKey(Integer temperatureId);

    int updateByPrimaryKeySelective(ZsEcgInfo record);

    int updateByPrimaryKeyWithBLOBs(ZsEcgInfo record);

    int updateByPrimaryKey(ZsEcgInfo record);

    /**
     * 获取心电贴最新心电数据
     *
     * @param paraMap
     * @return
     */
    ZsEcgInfo getNewEcg(Map<String, Object> paraMap);

    List<EcgHistory> getEcgHistoryAsJson(Long paramLong);

    List<String> getExportHealth(String paramString);

    int deleteByMachineId(Integer paramInteger);
}