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
import com.yjjk.monitor.constant.MonitorRecord;
import com.yjjk.monitor.entity.ZsMachineInfo;
import com.yjjk.monitor.entity.ZsPatientRecord;
import com.yjjk.monitor.entity.json.TemperatureHistory;
import com.yjjk.monitor.entity.vo.PatientTemperature;
import com.yjjk.monitor.entity.vo.RecordHistory;
import com.yjjk.monitor.entity.vo.UseMachine;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.PatientRecordService;
import com.yjjk.monitor.utility.DateUtil;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CentreS
 * @Description: 历史记录
 * @create 2019/7/22
 */
@Service
public class PatientRecordServiceImpl extends BaseService implements PatientRecordService {

    @Override
    public int addPatientRecord(ZsPatientRecord patientRecord) {
        ZsMachineInfo machineInfo = new ZsMachineInfo();
        if (patientRecord.getMachineId() != null) {
            // 修改设备的使用状态
            machineInfo.setMachineId(patientRecord.getMachineId()).setUsageState(2);
            super.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
        }
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
        List<UseMachine> monitorList = super.ZsPatientRecordMapper.getMonitorsInfo(departmentId);
        List<PatientTemperature> temperatureList = super.ZsPatientRecordMapper.getMinitorsTemperature(departmentId);
        for (int i = 0; i < monitorList.size(); i++) {
            // 初始化监控状态为：未使用
            monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_UNUSED);
            if (monitorList.get(i).getRecordId() == null){
                continue;
            }
            for (int j = 0; j < temperatureList.size(); j++) {
                if (monitorList.get(i).getMachineId() == temperatureList.get(j).getMachineId()) {
                    Long recordTime = DateUtil.timeDifferentLong(monitorList.get(i).getStartTime(), DateUtil.getCurrentTime());
                    // 监测时间小于10分钟为预热中
                    if (recordTime <= 10) {
                        monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_READY);
                    } else {
                        monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_USAGE);
                        // 连接异常判断：最后一条体温数据为3分钟前的数据
                        Long temperatureTimeDifferent = DateUtil.timeDifferentLong(temperatureList.get(j).getCreateTime(), DateUtil.getCurrentTime());
                        if (temperatureTimeDifferent >= 3) {
                            monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_ERR);
                        }
                    }
                    // 填充体温数据
                    monitorList.get(i).setUseTimes(DateUtil.timeDifferent(monitorList.get(i).getStartTime(), monitorList.get(i).getEndTime()));
                    monitorList.get(i).setTemperature(temperatureList.get(j).getTemperature());
                    monitorList.get(i).setTemperatureStatus(temperatureList.get(j).getTemperatureStatus());
                    monitorList.get(i).setPattery(temperatureList.get(j).getPattery());
                    break;
                }
            }
        }
        return monitorList;
    }

//    @Override
//    @Deprecated
//    public List<PatientTemperature> getMinitorsTemperature(Integer departmentId) {
//        List<PatientTemperature> list = super.ZsPatientRecordMapper.getMinitorsTemperature(departmentId);
//        for (int i = 0; i < list.size(); i++) {
//            list.get(i).setUseTimes(DateUtil.timeDifferent(list.get(i).getStartTime(), list.get(i).getEndTime()));
//        }
//        return list;
//    }

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
        ZsPatientRecord patientRecord = new ZsPatientRecord();
        patientRecord.setPatientId(patientId);
        patientRecord.setUsageState(1);
        patientRecord.setEndTime(DateUtil.getCurrentTime());
        int x = super.ZsPatientRecordMapper.updateSelectiveByPatientId(patientRecord);

        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("endTime", patientRecord.getEndTime());
        paraMap.put("patientId", patientId);
        List<TemperatureHistory> list = super.ZsPatientRecordMapper.selectTemperatureHistory(paraMap);
        List<TemperatureHistory> resultList = new ArrayList<>();

        //
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("patientId", patientId);
        tempMap.put("machineId", machineId);
        ZsPatientRecord zsPatientRecord = super.ZsPatientRecordMapper.selectByPatientAndMachine(tempMap);
        // 根据监测时常选择获取数据的数据间隔
        Integer interval = DateUtil.getInterval(DateUtil.timeDifferentLong(zsPatientRecord.getStartTime(), (String) paraMap.get("endTime")));
        for (int i = 0; i < list.size(); i += interval) {
            resultList.add(list.get(i));
        }
        // 将历史体温写回patient_record表
        patientRecord.setTemperatureHistory(JSON.toJSONString(resultList));
        int z = super.ZsPatientRecordMapper.updateSelectiveByPatientId(patientRecord);

        ZsMachineInfo machineInfo = new ZsMachineInfo();
        // 修改设备的使用状态
        machineInfo.setMachineId(machineId).setUsageState(0);
        int j = super.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);

        if (z == 0 || j == 0 || x == 0) {
            throw new RuntimeException("停止失败");
        }
        return z;
    }

    @Override
    public List<TemperatureHistory> getCurrentTemperatureRecord(Map<String, Object> paraMap) {
        List<TemperatureHistory> list = super.ZsPatientRecordMapper.selectTemperatureHistory(paraMap);
        List<TemperatureHistory> temp = new ArrayList<>();

        Integer interval = DateUtil.getInterval((Long) paraMap.get("times"));
        for (int i = 0; i < list.size(); i += interval) {
            temp.add(list.get(i));
        }
        return temp;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean changeMachine(Integer machineId1, Integer machineId2) {
        ZsMachineInfo machineInfo = new ZsMachineInfo();
        // 修改设备的使用状态
        machineInfo.setMachineId(machineId1).setUsageState(0);
        int i = super.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
        machineInfo.setMachineId(machineId2).setUsageState(2);
        int j = super.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
        if (i == 0 || j == 0) {
            return false;
        }
        return true;
    }

    @Override
    public int selectByBedId(Integer bedId) {
        return super.ZsPatientRecordMapper.selectByBedId(bedId);
    }
}
