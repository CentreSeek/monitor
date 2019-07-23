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
 * @Description: 病人管理模块
 * @author CentreS
 * @create 2019/7/22
 */
@Service
public class PatientServiceImpl extends BaseService implements PatientService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ZsPatientInfo addPatient(String name, String caseNum, Integer bedId) {
        ZsPatientInfo zsPatientInfo = super.ZsPatientInfoMapper.selectByCaseNum(caseNum);
        if (zsPatientInfo == null){
            zsPatientInfo.setBedId(bedId).setName(name).setCaseNum(caseNum);
            super.ZsPatientInfoMapper.insertSelective(zsPatientInfo);
            zsPatientInfo = super.ZsPatientInfoMapper.selectByCaseNum(caseNum);
        }
        return zsPatientInfo;
    }
}
