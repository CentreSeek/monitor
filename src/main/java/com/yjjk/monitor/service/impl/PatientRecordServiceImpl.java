/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: PatientRecordServiceImpl
 * Author:   CentreS
 * Date:     2019/7/22 11:41
 * Description: 历史记录
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.yjjk.monitor.entity.ZsMachineInfo;
import com.yjjk.monitor.entity.ZsPatientRecord;
import com.yjjk.monitor.entity.json.TemperatureHistory;
import com.yjjk.monitor.entity.vo.PatientTemperature;
import com.yjjk.monitor.entity.vo.RecordHistory;
import com.yjjk.monitor.entity.vo.UseMachine;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.PatientRecordService;
import com.yjjk.monitor.utility.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 历史记录
 * @author CentreS
 * @create 2019/7/22
 */
@Service
public class PatientRecordServiceImpl extends BaseService implements PatientRecordService {


    @Override
    public int addPatientRecord(ZsPatientRecord patientRecord) {
        ZsMachineInfo machineInfo = new ZsMachineInfo();
        if (patientRecord.getMachineId() != null){
            // 修改设备的使用状态
            machineInfo.setMachineId(patientRecord.getMachineId()).setUsageState(2);
        }
        super.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
        return super.ZsPatientRecordMapper.insertSelective(patientRecord);
    }

    @Override
    public int updateByPrimaryKey(ZsPatientRecord patientRecord) {
        return super.ZsPatientRecordMapper.updateByPrimaryKeySelective(patientRecord);
    }

    @Override
    public ZsPatientRecord selectByPrimaryKey(Long recordId) {
        return super.ZsPatientRecordMapper.selectByPrimaryKey(recordId);
    }

    @Override
    public List<UseMachine> getMonitorsInfo(Integer departmentId) {
        return super.ZsPatientRecordMapper.getMonitorsInfo(departmentId);
    }

    @Override
    public List<PatientTemperature> getMinitorsTemperature(Integer departmentId) {
        return super.ZsPatientRecordMapper.getMinitorsTemperature(departmentId);
    }

    @Override
    public List<RecordHistory> getRecordHistory(RecordHistory recordHistory) {
        return super.ZsPatientRecordMapper.getRecordHistory(recordHistory);
    }

    @Override
    public int getRecordHistoryCount(RecordHistory recordHistory) {
        return super.ZsPatientRecordMapper.getRecordHistoryCount(recordHistory);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int stopMonitoring(Integer patientId, Integer machineId) {
        List<TemperatureHistory> list = super.ZsPatientRecordMapper.selectTemperatureHistory(patientId);
        List<TemperatureHistory> resultList = new ArrayList<>();
        // 每隔十分钟取一条数据
        for (int i = 0; i < list.size(); i += 10) {
            resultList.add(list.get(i));
        }

        ZsMachineInfo machineInfo = new ZsMachineInfo();
            // 修改设备的使用状态
        machineInfo.setMachineId(machineId).setUsageState(0);
        int j = super.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);

        ZsPatientRecord patientRecord = new ZsPatientRecord();
        patientRecord.setPatientId(patientId);
        patientRecord.setTemperatureHistory(JSON.toJSONString(resultList));
        patientRecord.setUsageState(1);
        patientRecord.setEndTime(DateUtil.getCurrentTime());
        int i = super.ZsPatientRecordMapper.updateSelectiveByPatientId(patientRecord);
        if (i == 0 || j == 0){
            throw new RuntimeException("停止失败");
        }
        return i;
    }

    @Override
    public List<TemperatureHistory> getCurrentTemperatureRecord(Integer patientId) {
        return super.ZsPatientRecordMapper.selectTemperatureHistory(patientId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean changeMachine(Integer machineId1, Integer machineId2) {
        ZsMachineInfo machineInfo = new ZsMachineInfo();
        // 修改设备的使用状态
        machineInfo.setMachineId(machineId1).setUsageState(0);
        int i = super.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
        machineInfo.setMachineId(machineId1).setUsageState(2);
        int j = super.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
        if (i == 0 || j == 0){
            return false;
        }
        return true;
    }
}
