/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: PatientServiceImpl
 * Author:   CentreS
 * Date:     2019/7/22 10:31
 * Description: 病人管理模块
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service.impl;

import com.yjjk.monitor.entity.ZsPatientInfo;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.PatientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author CentreS
 * @Description: 病人管理模块
 * @create 2019/7/22
 */
@Service
public class PatientServiceImpl extends BaseService implements PatientService {

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ZsPatientInfo addPatient(String name, String caseNum, Integer bedId, Integer departmentId) {
        ZsPatientInfo zsPatientInfo = this.ZsPatientInfoMapper.selectByCaseNum(caseNum);
        if (zsPatientInfo == null) {
            zsPatientInfo = new ZsPatientInfo();
            zsPatientInfo.setBedId(bedId).setName(name).setCaseNum(caseNum).setDepartmentId(departmentId);
            this.ZsPatientInfoMapper.insertSelective(zsPatientInfo);
            zsPatientInfo = this.ZsPatientInfoMapper.selectByCaseNum(caseNum);
        } else {
            zsPatientInfo.setBedId(bedId).setName(name).setDepartmentId(departmentId);
            this.ZsPatientInfoMapper.updateByPrimaryKeySelective(zsPatientInfo);
        }
        return zsPatientInfo;
    }

    @Override
    public ZsPatientInfo selectByCaseNum(String caseNum) {
        return this.ZsPatientInfoMapper.selectByCaseNum(caseNum);
    }

    @Override
    public int updateName(ZsPatientInfo record) {
        return this.ZsPatientInfoMapper.updateName(record);
    }
}
