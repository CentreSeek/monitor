/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: PatientService
 * Author:   CentreS
 * Date:     2019/7/22 10:30
 * Description: 病人模块
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service;

import com.yjjk.monitor.entity.ZsPatientInfo;

/**
 * @Description: 病人模块
 * @author CentreS
 * @create 2019/7/22
 */
public interface PatientService {

    /**
     * 新增病人
     * @param name
     * @param caseNum
     * @param bedId
     * @return
     */
    ZsPatientInfo addPatient(String name, String caseNum, Integer bedId, Integer departmentId);

    /**
     * 使用病历号查询病人
     * @param caseNum
     * @return
     */
    ZsPatientInfo selectByCaseNum(String caseNum);
}
