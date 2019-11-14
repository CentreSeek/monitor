/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: EcgServiceImpl
 * Author:   CentreS
 * Date:     2019/11/13 10:04
 * Description: 心电模块
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service.impl;

import com.yjjk.monitor.constant.EcgConstant;
import com.yjjk.monitor.entity.ZsHealthInfo;
import com.yjjk.monitor.entity.vo.EcgMonitorVO;
import com.yjjk.monitor.entity.vo.UseMachineVO;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.EcgService;
import com.yjjk.monitor.utility.DateUtil;
import com.yjjk.monitor.utility.ReflectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CentreS
 * @Description: 心电模块
 * @create 2019/11/13
 */
@Service
public class EcgServiceImpl extends BaseService implements EcgService {

    @Override
    public List<UseMachineVO> getMonitorsInfo(Integer departmentId) {
        List<UseMachineVO> monitorsInfoForEcg = super.ZsPatientRecordMapper.getMonitorsInfoForEcg(departmentId);
        return monitorsInfoForEcg;
    }

    @Override
    public List<EcgMonitorVO> updateUseMachine(List<UseMachineVO> monitorsInfo, Integer departmentId) {
        List<EcgMonitorVO> list = new ArrayList<>();
        List<ZsHealthInfo> healthInfo = super.zsHealthInfoMapper.getHealthInfo(departmentId);
        for (int i = 0; i < monitorsInfo.size(); i++) {
            EcgMonitorVO ecgMonitorVO = ReflectUtils.transformToBean(monitorsInfo.get(i), EcgMonitorVO.class);
            // 初始化连接状态为 2：未使用
            ecgMonitorVO.setRecordState(2);
            for (int j = 0; j < healthInfo.size(); j++) {
                if (ecgMonitorVO.getMachineId() == null) {
                    break;
                } else if (ecgMonitorVO.getMachineId() == Integer.parseInt(healthInfo.get(j).getMachineId())) {
                    ecgMonitorVO.setHeartRate(healthInfo.get(j).getHeartRate());
                    ecgMonitorVO.setRespiratoryRate(healthInfo.get(j).getRespiratoryRate());
                    ecgMonitorVO.setUseTimes(DateUtil.timeDifferent(monitorsInfo.get(i).getStartTime(),monitorsInfo.get(i).getEndTime()));
                    // 连接状态
                    if (DateUtil.getCurrentTimeLong() - healthInfo.get(j).getTimestamp() >= EcgConstant.FIVE_MINUTES_LONG) {
                        ecgMonitorVO.setRecordState(1);
                    } else {
                        ecgMonitorVO.setRecordState(0);
                    }
                    // 阈值
                    if (healthInfo.get(j).getHeartRate().doubleValue() > EcgConstant.HEART_RATE_HEIGHT || healthInfo.get(j).getHeartRate().doubleValue() < EcgConstant.HEART_RATE_LOW) {
                        ecgMonitorVO.setHeartRateStatus(1);
                    } else {
                        ecgMonitorVO.setHeartRateStatus(0);
                    }
                    if (healthInfo.get(j).getRespiratoryRate().doubleValue() > EcgConstant.RESPIRATORY_RATE_HEIGHT || healthInfo.get(j).getRespiratoryRate().doubleValue() < EcgConstant.RESPIRATORY_RATE_LOW) {
                        ecgMonitorVO.setRespiratoryRateStatus(1);
                    } else {
                        ecgMonitorVO.setRespiratoryRateStatus(0);
                    }
                }
            }
            list.add(ecgMonitorVO);
        }
        return list;
    }
}
