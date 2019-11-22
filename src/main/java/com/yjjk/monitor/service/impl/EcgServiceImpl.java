package com.yjjk.monitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.yjjk.monitor.constant.EcgConstant;
import com.yjjk.monitor.entity.ZsHealthInfo;
import com.yjjk.monitor.entity.ZsMachineInfo;
import com.yjjk.monitor.entity.ZsPatientRecord;
import com.yjjk.monitor.entity.json.HealthHistory;
import com.yjjk.monitor.entity.transaction.BackgroundResult;
import com.yjjk.monitor.entity.transaction.BackgroundSend;
import com.yjjk.monitor.entity.vo.EcgMonitorVO;
import com.yjjk.monitor.entity.vo.HealthHistoryVO;
import com.yjjk.monitor.entity.vo.UseMachineVO;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.EcgService;
import com.yjjk.monitor.utility.CalculateUtils;
import com.yjjk.monitor.utility.DateUtil;
import com.yjjk.monitor.utility.ExcelUtils;
import com.yjjk.monitor.utility.FileNameUtils;
import com.yjjk.monitor.utility.FileUtils;
import com.yjjk.monitor.utility.NetUtils;
import com.yjjk.monitor.utility.ReflectUtils;
import com.yjjk.monitor.utility.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 心电模块
 */
@Service
public class EcgServiceImpl extends BaseService implements EcgService {
    @Override
    public List<UseMachineVO> getMonitorsInfo(Integer departmentId) {
        List<UseMachineVO> monitorsInfoForEcg = this.ZsPatientRecordMapper.getMonitorsInfoForEcg(departmentId);
        return monitorsInfoForEcg;
    }

    @Override
    public List<EcgMonitorVO> updateUseMachine(List<UseMachineVO> monitorsInfo, Integer departmentId) {
        List<EcgMonitorVO> list = new ArrayList();
        List<ZsHealthInfo> healthInfo = this.zsHealthInfoMapper.getHealthInfo(departmentId);
        for (int i = 0; i < monitorsInfo.size(); i++) {
            EcgMonitorVO ecgMonitorVO = ReflectUtils.transformToBean(monitorsInfo.get(i), EcgMonitorVO.class);

            ecgMonitorVO.setRecordState(Integer.valueOf(2));
            if (ecgMonitorVO.getMachineId() != null) {
                for (int j = 0; j < healthInfo.size(); j++) {
                    if (ecgMonitorVO.getMachineId().intValue() == Integer.parseInt((healthInfo.get(j)).getMachineId())) {
                        ecgMonitorVO.setHeartRate((healthInfo.get(j)).getHeartRate()).setPattery((monitorsInfo.get(i)).getPattery().toString());
                        ecgMonitorVO.setRespiratoryRate((healthInfo.get(j)).getRespiratoryRate());
                        ecgMonitorVO.setUseTimes(DateUtil.timeDifferent((monitorsInfo.get(i)).getStartTime(),
                                (monitorsInfo.get(i)).getEndTime()));
                        // 如果为三分钟前的数据则判定为 1：连接异常
                        if (DateUtil.getCurrentTimeLong().longValue() - (healthInfo.get(j)).getTimestamp().longValue() >= 300000L) {
                            ecgMonitorVO.setRecordState(Integer.valueOf(1));
                        } else {
                            ecgMonitorVO.setRecordState(Integer.valueOf(0));
                        }
                        if (((healthInfo.get(j)).getHeartRate().doubleValue() > 100.0D) || ((healthInfo.get(j)).getHeartRate().doubleValue() < 50.0D)) {
                            ecgMonitorVO.setHeartRateStatus(Integer.valueOf(1));
                        } else {
                            ecgMonitorVO.setHeartRateStatus(Integer.valueOf(0));
                        }
                        if (((healthInfo.get(j)).getRespiratoryRate().doubleValue() > 24.0D) || ((healthInfo.get(j)).getRespiratoryRate().doubleValue() < 12.0D)) {
                            ecgMonitorVO.setRespiratoryRateStatus(Integer.valueOf(1));
                        } else {
                            ecgMonitorVO.setRespiratoryRateStatus(Integer.valueOf(0));
                        }
                    }
                    if (j == healthInfo.size() - 1 && ecgMonitorVO.getMachineId().intValue() != Integer.parseInt((healthInfo.get(j)).getMachineId())) {
                        ecgMonitorVO.setRecordState(Integer.valueOf(1));
                    }
                }
            }
            list.add(ecgMonitorVO);
        }
        return list;
    }

    @Override
    public List<HealthHistory> getHealthHistory(Long recordId) {
        return this.zsHealthInfoMapper.getHealthHistory(recordId);
    }

    @Override
    public HealthHistoryVO parseRateHistory(List<HealthHistory> list, HealthHistoryVO healthHistoryVO) {
        Double heartRateMax = Double.valueOf(0.0D);
        Double heartRateMin = Double.valueOf(1000.0D);
        BigDecimal heartRateAvg = new BigDecimal("0");
        List<HealthHistory> paraList = new ArrayList();
        int count = 0;
        if (StringUtils.isNullorEmpty(list)) {
            heartRateMax = null;
            heartRateMin = null;
        } else {
            for (HealthHistory tmp : list) {
                heartRateAvg = CalculateUtils.avg(heartRateAvg, new BigDecimal(tmp.getHeartRate()));
                if (Double.parseDouble(tmp.getHeartRate()) > heartRateMax.doubleValue()) {
                    heartRateMax = Double.valueOf(Double.parseDouble(tmp.getHeartRate()));
                }
                if (Double.parseDouble(tmp.getHeartRate()) < heartRateMin.doubleValue()) {
                    heartRateMin = Double.valueOf(Double.parseDouble(tmp.getHeartRate()));
                }
                if (count == 0) {
                    paraList.add(tmp);
                    count++;
                }
                if (count == 30) {
                    count = 0;
                }
            }
        }
        healthHistoryVO.setHighestHeartRate(heartRateMax).setLowestHeartRate(heartRateMin).setAvgHeartRate(Double.valueOf(heartRateAvg.setScale(1,
                RoundingMode.HALF_UP).doubleValue())).setList(paraList);
        return healthHistoryVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean stopEcg(ZsPatientRecord zsPatientRecord) {
        String endTime = DateUtil.getCurrentTime();
        List<HealthHistory> healthList = this.zsHealthInfoMapper.getHealthHistory(zsPatientRecord.getRecordId());

        ZsPatientRecord paraPatientRecord = new ZsPatientRecord();
        if (healthList != null) {
            paraPatientRecord.setRateHistory(JSON.toJSONString(healthList));
        }
        paraPatientRecord.setRecordId(zsPatientRecord.getRecordId());
        paraPatientRecord.setPatientId(zsPatientRecord.getPatientId());
        paraPatientRecord.setUsageState(Integer.valueOf(1));
        paraPatientRecord.setEndTime(endTime);
        int z = this.ZsPatientRecordMapper.updateSelectiveByPatientId(paraPatientRecord);

        ZsMachineInfo machineInfo = new ZsMachineInfo();

        machineInfo.setMachineId(zsPatientRecord.getMachineId()).setUsageState(Integer.valueOf(0));
        int j = this.ZsMachineInfoMapper.updateByPrimaryKeySelective(machineInfo);

        String date = DateUtil.getOneMonthAgo();
        List<String> list = this.zsEcgInfoMapper.getExportHealth(date);
        ExcelUtils.writeToTxt(list, "\\ExportData\\EcgExport");
        int k = this.zsEcgInfoMapper.deleteByMachineId(zsPatientRecord.getMachineId());
        if ((z == 0) || (j == 0)) {
            return false;
        }
        return true;
    }

    @Override
    public int cleanEcgExport() {
        String path = FileUtils.getRootPath() + "\\ExportData\\EcgExport";
        File file = new File(path);
        String[] list = file.list();
        Long sevenDaysAgo = Long.valueOf(DateUtil.getCurrentTimeLong().longValue() - 604800000L);
        int count = 0;
        try {
            for (String s : list) {
                String fileDate = FileNameUtils.getPrefix(s);
                if (sevenDaysAgo.longValue() > Long.valueOf(fileDate).longValue()) {
                    File tempFile = new File(path + "\\\\" + s);
                    FileUtils.delFile(tempFile);
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public BackgroundResult connectEcgMachine(Integer machineId, Integer bedId, String connectionType) {
        try {
            // 连接心电设备
            BackgroundSend backgroundSend = new BackgroundSend();
            backgroundSend.setActionId(backgroundSend.getActionId());
            int repeaterId = super.zsRepeaterInfoMapper.selectByBedId(bedId);
            if (StringUtils.isNullorEmpty(repeaterId)) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            backgroundSend.setDeviceId(String.valueOf(repeaterId));
            backgroundSend.setData(connectionType);
            String s = NetUtils.doPost(EcgConstant.ECG_CONNECTION_URL, backgroundSend);
            return JSON.parseObject(s, BackgroundResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }
}
