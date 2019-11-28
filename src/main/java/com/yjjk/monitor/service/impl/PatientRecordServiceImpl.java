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
import com.yjjk.monitor.entity.export.HealthRecordHistory2Excel;
import com.yjjk.monitor.entity.export.RecordHistory2Excel;
import com.yjjk.monitor.entity.json.TemperatureHistory;
import com.yjjk.monitor.entity.vo.PatientTemperature;
import com.yjjk.monitor.entity.vo.RecordHistory;
import com.yjjk.monitor.entity.vo.UseMachineVO;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.PatientRecordService;
import com.yjjk.monitor.utility.DateUtil;
import com.yjjk.monitor.utility.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int addPatientRecord(ZsPatientRecord patientRecord){
        ZsMachineInfo machineInfo = new ZsMachineInfo();
        machineInfo.setMachineId(patientRecord.getMachineId()).setUsageState(Integer.valueOf(2));
        this.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
        return this.ZsPatientRecordMapper.insertSelective(patientRecord);
    }

    @Override
    public int updateByPrimaryKey(ZsPatientRecord patientRecord) {
        return this.ZsPatientRecordMapper.updateByPrimaryKeySelective(patientRecord);
    }

    @Override
    public ZsPatientRecord selectByPrimaryKey(Long recordId) {
        return this.ZsPatientRecordMapper.selectByRecordId(recordId);
    }

    @Override
    public List<UseMachineVO> getMonitorsInfo(Integer departmentId) {
        List<UseMachineVO> monitorList = this.ZsPatientRecordMapper.getMonitorsInfo(departmentId);

        return monitorList;
    }

    @Override
    public List<UseMachineVO> selectiveByBedId(List<UseMachineVO> list, Integer start, Integer end) {
        if (end.intValue() < start.intValue()) {
            return null;
        }
        Iterator<UseMachineVO> iter = list.iterator();
        while (iter.hasNext()) {
            UseMachineVO item = (UseMachineVO) iter.next();
            int bedId = item.getBedId().intValue();
            if ((bedId < start.intValue()) || (bedId > end.intValue())) {
                iter.remove();
            }
        }
        return list;
    }

    @Override
    public List<UseMachineVO> isUsed(List<UseMachineVO> list) {
        Iterator<UseMachineVO> iter = list.iterator();
        while (iter.hasNext()) {
            UseMachineVO item = (UseMachineVO) iter.next();
            if (item.getRecordId() == null) {
                iter.remove();
            }
        }
        return list;
    }

    @Override
    public List<RecordHistory> getRecordHistory(RecordHistory recordHistory) {
        return this.ZsPatientRecordMapper.getRecordHistory(recordHistory);
    }

    @Override
    public int getRecordHistoryCount(RecordHistory recordHistory) {
        return this.ZsPatientRecordMapper.getRecordHistoryCount(recordHistory);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public int stopMonitoring(ZsPatientRecord patientRecord) {
        Map<String, Object> paraMap = new HashMap();
        String endTime = DateUtil.getCurrentTime();
        paraMap.put("endTime", endTime);
        paraMap.put("patientId", patientRecord.getPatientId());
        List<TemperatureHistory> list = this.ZsPatientRecordMapper.selectTemperatureHistory(paraMap);

        ZsPatientRecord paraPatientRecord = new ZsPatientRecord();
        if (list != null) {
            paraPatientRecord.setTemperatureHistory(JSON.toJSONString(list));
        }
        paraPatientRecord.setPatientId(patientRecord.getPatientId());
        paraPatientRecord.setUsageState(Integer.valueOf(1));
        paraPatientRecord.setEndTime(endTime);

        int z = this.ZsPatientRecordMapper.updateSelectiveByPatientId(paraPatientRecord);

        ZsMachineInfo machineInfo = new ZsMachineInfo();

        machineInfo.setMachineId(patientRecord.getMachineId()).setUsageState(Integer.valueOf(0));
        int j = this.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
        if ((z == 0) || (j == 0)) {
            throw new RuntimeException("数据录入错误");
        }
        return z;
    }

    @Override
    public List<TemperatureHistory> getCurrentTemperatureRecord(Map<String, Object> paraMap) {
        return this.ZsPatientRecordMapper.selectTemperatureHistory(paraMap);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public boolean changeMachine(Integer machineId1, Integer machineId2) {
        ZsMachineInfo machineInfo = new ZsMachineInfo();

        machineInfo.setMachineId(machineId1).setUsageState(Integer.valueOf(0));
        int i = this.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
        machineInfo.setMachineId(machineId2).setUsageState(Integer.valueOf(2));
        int j = this.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);
        if ((i == 0) || (j == 0)) {
            return false;
        }
        return true;
    }

    @Override
    public int selectByBedId(Integer bedId) {
        return this.ZsPatientRecordMapper.selectByBedId(bedId);
    }

    @Override
    public ZsPatientRecord selectByPatientId(Integer patientId) {
        return this.ZsPatientRecordMapper.selectByPatientId(patientId);
    }

    @Override
    public List<RecordHistory2Excel> getExportList(Map<String, Object> paraMap) {
        Integer departmentId = this.zsLoginStateMapper.selectDepartmentIdByToken(String.valueOf(paraMap.get("token")));
        if (!StringUtils.isNullorEmpty(departmentId)) {
            paraMap.put("departmentId", departmentId);
        }
        return this.ZsPatientRecordMapper.getExportList(paraMap);
    }

    @Override
    public List<HealthRecordHistory2Excel> getHealthExportList(Map<String, Object> paraMap) {
        Integer departmentId = this.zsLoginStateMapper.selectDepartmentIdByToken(String.valueOf(paraMap.get("token")));
        if (!StringUtils.isNullorEmpty(departmentId)) {
            paraMap.put("departmentId", departmentId);
        }
        return this.ZsPatientRecordMapper.getHealthExportList(paraMap);
    }

    @Override
    public Map<String, Object> parseTemperature(List<TemperatureHistory> list, Map<String, Object> paraMap, Integer machineId) {
        if (list == null) {
            return null;
        }
        ZsMachineInfo zsMachineInfo = this.ZsMachineInfoMapper.selectByPrimaryKey(machineId);
        if (zsMachineInfo == null) {
            return null;
        }
        ZsTemperatureBound zsTemperatureBound = this.zsTemperatureBoundMapper.selectByPrimaryKey(zsMachineInfo.getDepartmentId());
        if (zsTemperatureBound == null) {
            zsTemperatureBound = this.zsTemperatureBoundMapper.selectByPrimaryKey(TemperatureConstant.DEFAULT_DEPARTMENT_ID);
        }
        int count = 0;
        boolean flag = false;
        double highestTemperature = 0.0D;
        for (int i = 0; i < list.size(); i++) {
            if (Double.parseDouble(((TemperatureHistory) list.get(i)).getTemperature()) >= zsTemperatureBound.getHighAlert().doubleValue()) {
                flag = true;
                if (highestTemperature < Double.parseDouble(((TemperatureHistory) list.get(i)).getTemperature())) {
                    highestTemperature = Double.parseDouble(((TemperatureHistory) list.get(i)).getTemperature());
                }
            } else if (flag == true) {
                flag = false;
                count++;
            }
            if ((flag == true) && (i == list.size() - 1)) {
                count++;
            }
        }
        paraMap.put("highestTemperatureCount", Integer.valueOf(count));
        paraMap.put("highestTemperature", Double.valueOf(highestTemperature));
        return paraMap;
    }

    @Override
    public List<UseMachineVO> updateTemperature(List<UseMachineVO> monitorList, Integer departmentId) {
        List<PatientTemperature> temperatureList = super.ZsPatientRecordMapper.getMinitorsTemperature(departmentId);
        for (int i = 0; i < monitorList.size(); i++) {
            // 初始化监控状态为：未使用
            monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_UNUSED);
            if (monitorList.get(i).getRecordId() != null) {
                monitorList.get(i).setUseTimes(DateUtil.timeDifferent(monitorList.get(i).getStartTime(), monitorList.get(i).getEndTime()));
                monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_USAGE);
                for (int j = 0; j < temperatureList.size(); j++) {
                    if (monitorList.get(i).getMachineId().equals(temperatureList.get(j).getMachineId())) {
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
                            // 是否佩戴判断：最后一条体温数据为60分钟前的数据或无温度数据
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
                // 填补特殊漏洞
                if (temperatureList.size() == 0 || monitorList.get(i).getTemperature() == null) {
                    monitorList.get(i).setRecordState(MonitorRecord.RECORD_STATE_ERR_WEAR);
                }
            }
        }
        return monitorList;
    }

}
