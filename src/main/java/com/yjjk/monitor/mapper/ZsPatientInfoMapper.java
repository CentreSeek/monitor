package com.yjjk.monitor.mapper;


import com.yjjk.monitor.entity.ZsPatientInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

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
     *
     * @param caseNum
     * @return
     */
    ZsPatientInfo selectByCaseNum(String caseNum);

    /**
     * 更新病人姓名
     *
     * @param record
     * @return
     */
    @Update("update zs_patient_info " +
            "set `name` = #{name,jdbcType=VARCHAR},department_id = #{departmentId,jdbcType=INTEGER}, room_id = #{roomId,jdbcType=INTEGER}, bed_id = #{bedId,jdbcType=INTEGER} " +
            "where patient_id = #{patientId,jdbcType=INTEGER}")
    int updateName(ZsPatientInfo record);
}