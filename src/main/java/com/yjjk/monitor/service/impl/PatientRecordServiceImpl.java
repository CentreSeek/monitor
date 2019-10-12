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
import com.yjjk.monitor.constant.TemperatureConstant;
import com.yjjk.monitor.entity.ZsMachineInfo;
import com.yjjk.monitor.entity.ZsPatientRecord;
import com.yjjk.monitor.entity.ZsTemperatureBound;
import com.yjjk.monitor.entity.json.TemperatureHistory;
import com.yjjk.monitor.entity.vo.PatientTemperature;
import com.yjjk.monitor.entity.vo.RecordHistory;
import com.yjjk.monitor.entity.vo.RecordHistory2Excel;
import com.yjjk.monitor.entity.vo.UseMachineVO;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.PatientRecordService;
import com.yjjk.monitor.utility.DateUtil;
import com.yjjk.monitor.utility.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
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
        return super.ZsPatientRecordMapper.selectByRecordId(recordId);
    }

    @Override
    public List<UseMachineVO> getMonitorsInfo(Integer departmentId) {
        List<UseMachineVO> monitorList = super.ZsPatientRecordMapper.getMonitorsInfo(departmentId);
        List<PatientTemperature> temperatureList = super.ZsPatientRecordMapper.getMinitorsTemperature(departmentId);
        for (int i = 0; i < monitorList.size(); i++) {
            // 初始化监控状态为：未使用
            monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_UNUSED);
            if (monitorList.get(i).getRecordId() == null) {
                continue;
            } else {
//                monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_ERR);
                monitorList.get(i).setUseTimes(DateUtil.timeDifferent(monitorList.get(i).getStartTime(), monitorList.get(i).getEndTime()));
                for (int j = 0; j < temperatureList.size(); j++) {
                    if (monitorList.get(i).getMachineId() == temperatureList.get(j).getMachineId()) {
                        Long recordTime = DateUtil.timeDifferentLong(monitorList.get(i).getStartTime(), DateUtil.getCurrentTime());
                        // 监测时间小于2分钟为预热中
                        if (recordTime <= 2) {
                            monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_READY);
                        } else {
                            monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_USAGE);
                            // 温度小于30℃提示未按规范粘贴
                            if (Double.parseDouble(temperatureList.get(j).getTemperature()) < 30.0) {
                                monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_ERR_USED);
                            }
                            Long temperatureTimeDifferent = DateUtil.timeDifferentLong(temperatureList.get(j).getCreateTime(),
                                    DateUtil.getCurrentTime());
                            // 连接异常判断：最后一条体温数据为3分钟前的数据
                            if (temperatureTimeDifferent >= 3) {
                                monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_ERR);
                            }
                            // 是否佩戴判断：最后一条体温数据为60分钟前的数据
                            if (temperatureTimeDifferent >= 60) {
                                monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_ERR_WEAR);
                            }
                        }
                        // 填充体温数据
                        monitorList.get(i).setTemperature(temperatureList.get(j).getTemperature());
                        monitorList.get(i).setTemperatureStatus(temperatureList.get(j).getTemperatureStatus());
                        monitorList.get(i).setPattery(temperatureList.get(j).getPattery());
                        break;
                    }
                }
            }
        }
        return monitorList;
    }

    @Override
    public List<UseMachineVO> selectiveByBedId(List<UseMachineVO> list, Integer start, Integer end) {
        if (end < start) {
            return null;
        }
        int bedId;
        Iterator<UseMachineVO> iter = list.iterator();
        while (iter.hasNext()) {
            UseMachineVO item = iter.next();
            bedId = item.getBedId();
            if (bedId < start || bedId > end) {
                iter.remove();
            }
        }
        return list;
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
    public int stopMonitoring(ZsPatientRecord patientRecord) {
        // 查询病人的所有体温记录
        Map<String, Object> paraMap = new HashMap<>();
        String endTime = DateUtil.getCurrentTime();
        paraMap.put("endTime", endTime);
        paraMap.put("patientId", patientRecord.getPatientId());
        List<TemperatureHistory> list = super.ZsPatientRecordMapper.selectTemperatureHistory(paraMap);
//        List<TemperatureHistory> resultList = new ArrayList<>();

        // 取头尾体温数据，后根据监测时常选择获取数据的数据间隔
//        Integer interval = DateUtil.getInterval(DateUtil.timeDifferentLong(patientRecord.getStartTime(), endTime));
//        for (int i = 0; i < list.size(); i += interval) {
//            resultList.add(list.get(i));
//        }
//        if (list.size() > 1) {
//            resultList.add(list.get(list.size() - 1));
//        }
        // 将历史体温写回patient_record表
        ZsPatientRecord paraPatientRecord = new ZsPatientRecord();
//        paraPatientRecord.setTemperatureHistory(JSON.toJSONString(resultList));
        if (list != null) {
            paraPatientRecord.setTemperatureHistory(JSON.toJSONString(list));
        }
        paraPatientRecord.setPatientId(patientRecord.getPatientId());
        paraPatientRecord.setUsageState(1);
        paraPatientRecord.setEndTime(endTime);

        int z = super.ZsPatientRecordMapper.updateSelectiveByPatientId(paraPatientRecord);

        ZsMachineInfo machineInfo = new ZsMachineInfo();

        // 修改设备的使用状态
        machineInfo.setMachineId(patientRecord.getMachineId()).setUsageState(0);
        int j = super.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);

        if (z == 0 || j == 0) {
            throw new RuntimeException("停止失败");
        }
        return z;
    }

    @Override
    public List<TemperatureHistory> getCurrentTemperatureRecord(Map<String, Object> paraMap) {
        return super.ZsPatientRecordMapper.selectTemperatureHistory(paraMap);
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

    @Override
    public ZsPatientRecord selectByPatientId(Integer patientId) {
        return super.ZsPatientRecordMapper.selectByPatientId(patientId);
    }

    @Override
    public List<RecordHistory2Excel> getExportList(Map<String, Object> paraMap) {
        Integer departmentId = super.zsLoginStateMapper.selectDepartmentIdByToken(String.valueOf(paraMap.get("token")));
        if (!StringUtils.isNullorEmpty(departmentId)) {
            paraMap.put("departmentId", departmentId);
        }
        return super.ZsPatientRecordMapper.getExportList(paraMap);
    }

    @Override
    public Map<String, Object> parseTemperature(List<TemperatureHistory> list, Map<String, Object> paraMap, Integer machineId) {
        if (list == null) {
            return null;
        }
        ZsMachineInfo zsMachineInfo = super.ZsMachineInfoMapper.selectByPrimaryKey(machineId);
        if (zsMachineInfo == null) {
            return null;
        }
        ZsTemperatureBound zsTemperatureBound = super.zsTemperatureBoundMapper.selectByPrimaryKey(zsMachineInfo.getDepartmentId());
        if (zsTemperatureBound == null) {
            zsTemperatureBound = super.zsTemperatureBoundMapper.selectByPrimaryKey(TemperatureConstant.DEFAULT_DEPARTMENT_ID);
        }
        // 统计高温数据
        int count = 0;
        boolean flag = false;
        double highestTemperature = 0.0;
        for (int i = 0; i < list.size(); i++) {

            if (Double.parseDouble(list.get(i).getTemperature()) >= zsTemperatureBound.getHighAlert()) {
                flag = true;
                if (highestTemperature < Double.parseDouble(list.get(i).getTemperature())) {
                    highestTemperature = Double.parseDouble(list.get(i).getTemperature());
                }
            } else {
                if (flag == true) {
                    flag = false;
                    count++;
                }
            }
            if (flag == true && i == list.size() - 1) {
                count++;
            }
        }
        paraMap.put("highestTemperatureCount", count);
        paraMap.put("highestTemperature", highestTemperature);
        return paraMap;
    }

}
