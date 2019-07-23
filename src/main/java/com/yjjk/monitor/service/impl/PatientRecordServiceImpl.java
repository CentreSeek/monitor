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

import com.yjjk.monitor.entity.ZsPatientRecord;
import com.yjjk.monitor.entity.vo.PatientTemperature;
import com.yjjk.monitor.entity.vo.UseMachine;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.PatientRecordService;

import java.util.List;

/**
 * @Description: 历史记录
 * @author CentreS
 * @create 2019/7/22
 */
public class PatientRecordServiceImpl extends BaseService implements PatientRecordService {


    @Override
    public int addPatientRecord(ZsPatientRecord patientRecord) {
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
    public List<UseMachine> getMonitorsInfo() {
        return super.ZsPatientRecordMapper.getMonitors();
    }

    @Override
    public List<PatientTemperature> getMinitorsTemperature() {
        return super.ZsPatientRecordMapper.getMinitorsTemperature();
    }
}
