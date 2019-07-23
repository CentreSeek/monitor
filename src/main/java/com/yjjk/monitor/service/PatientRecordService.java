/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: PatientRecordService
 * Author:   CentreS
 * Date:     2019/7/22 11:41
 * Description: 历史记录
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service;

import com.yjjk.monitor.entity.ZsPatientRecord;
import com.yjjk.monitor.entity.json.TemperatureHistory;
import com.yjjk.monitor.entity.vo.PatientTemperature;
import com.yjjk.monitor.entity.vo.RecordHistory;
import com.yjjk.monitor.entity.vo.UseMachine;

import java.util.List;

/**
 * @Description: 历史记录
 * @author CentreS
 * @create 2019/7/22
 */
public interface PatientRecordService {

    /**
     * 新增历史记录
     * @param patientRecord
     * @return
     */
    int addPatientRecord(ZsPatientRecord patientRecord);

    /**
     * 更新历史记录信息
     * @param patientRecord
     * @return
     */
    int updateByPrimaryKey(ZsPatientRecord patientRecord);

    /**
     * 查询历史记录
     * @param recordId
     * @return
     */
    ZsPatientRecord selectByPrimaryKey(Long recordId);

    /**
     * 获取监控列表
     * @return
     */
    List<UseMachine> getMonitorsInfo();

    /**
     * 获取实时监控信息
     * @return
     */
    List<PatientTemperature> getMinitorsTemperature();

    /**
     * 获取历史记录
     * @return
     */
    List<RecordHistory> getRecordHistory(RecordHistory recordHistory);

    /**
     * 获取历史记录总数
     * @param recordHistory
     * @return
     */
    int getRecordHistoryCount(RecordHistory recordHistory);

    /**
     * 停止监测
     * @return
     */
    int stopMonitoring(Integer patientId);

    /**
     * 获取实时体温记录
     * @param patientId
     * @return
     */
    List<TemperatureHistory> getCurrentTemperatureRecord(Integer patientId);

}
