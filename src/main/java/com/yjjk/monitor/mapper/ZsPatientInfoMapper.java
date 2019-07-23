package com.yjjk.monitor.mapper;


import com.yjjk.monitor.entity.ZsPatientInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ZsPatientInfoMapper {
    int deleteByPrimaryKey(Integer patientId);

    int insert(ZsPatientInfo record);

    int insertSelective(ZsPatientInfo record);

    ZsPatientInfo selectByPrimaryKey(Integer patientId);

    int updateByPrimaryKeySelective(ZsPatientInfo record);

    int updateByPrimaryKey(ZsPatientInfo record);

    /**
     * select---使用病历号查询病人
     * @param caseNum
     * @return
     */
    ZsPatientInfo selectByCaseNum(String caseNum);
}