package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsPatientRecord;
import com.yjjk.monitor.entity.vo.PatientTemperature;
import com.yjjk.monitor.entity.vo.UseMachine;

import java.util.List;

public interface ZsPatientRecordMapper {
    int deleteByPrimaryKey(Long recordId);

    int insert(ZsPatientRecord record);

    int insertSelective(ZsPatientRecord record);

    ZsPatientRecord selectByPrimaryKey(Long recordId);

    int updateByPrimaryKeySelective(ZsPatientRecord record);

    int updateByPrimaryKeyWithBLOBs(ZsPatientRecord record);

    int updateByPrimaryKey(ZsPatientRecord record);

    /**
     * select---获取监控列表
     * @return
     */
    List<UseMachine> getMonitors();

    /**
     * select---获取实时监控信息
     * @return
     */
    List<PatientTemperature> getMinitorsTemperature();
}