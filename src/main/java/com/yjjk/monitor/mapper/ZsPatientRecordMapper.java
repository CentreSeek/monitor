package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsPatientRecord;
import com.yjjk.monitor.entity.export.HealthRecordHistory2Excel;
import com.yjjk.monitor.entity.export.RecordHistory2Excel;
import com.yjjk.monitor.entity.json.TemperatureHistory;
import com.yjjk.monitor.entity.vo.PatientTemperature;
import com.yjjk.monitor.entity.vo.RecordHistory;
import com.yjjk.monitor.entity.vo.UseMachineVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ZsPatientRecordMapper {
    int deleteByPrimaryKey(Long recordId);

    int insert(ZsPatientRecord record);

    int insertSelective(ZsPatientRecord record);

    ZsPatientRecord selectByPrimaryKey(Long recordId);

    int updateByPrimaryKeySelective(ZsPatientRecord record);

    int updateByPrimaryKeyWithBLOBs(ZsPatientRecord record);

    int updateByPrimaryKey(ZsPatientRecord record);

    /**
     * select---获取体温监控列表
     * @return
     */
    List<UseMachineVO> getMonitorsInfo(Integer departmentId);

    /**
     * select---获取Ecg监控列表
     * @return
     */
    List<UseMachineVO> getMonitorsInfoForEcg(Integer departmentId);

    /**
     * select---获取实时监控信息
     * @return
     */
    List<PatientTemperature> getMinitorsTemperature(Integer departmentId);

    /**
     * select---获取历史记录
     * @return
     */
    List<RecordHistory> getRecordHistory(RecordHistory recordHistory);

    /**
     * select---获取历史记录总数
     * @param recordHistory
     * @return
     */
    int getRecordHistoryCount(RecordHistory recordHistory);

    /**
     * select---体温历史记录
     * @return
     */
    List<TemperatureHistory> selectTemperatureHistory(Map<String,Object> paraMap);

    /**
     * update---使用病人id更新历史记录表
     * @param patientRecord
     * @return
     */
    int updateSelectiveByPatientId(ZsPatientRecord patientRecord);

    /**
     * select---查询当前病床已绑定病人的数量
     * @param bedId
     * @return
     */
    int selectByBedId(Integer bedId);

    /**
     * select---使用patientId和machineId查询record信息
     * @param paraMap
     * @return
     */
    ZsPatientRecord selectByPatientAndMachine(Map<String, Object> paraMap);

    /**
     * select---使用patientId查询record信息
     * @param patientId
     * @return
     */
    ZsPatientRecord selectByPatientId(Integer patientId);

    /**
     * 体温记录导出
     * @param paraMap
     * @return
     */
    List<RecordHistory2Excel> getExportList(Map<String, Object> paraMap);

    List<HealthRecordHistory2Excel> getHealthExportList(Map<String, Object> paramMap);

    ZsPatientRecord selectByRecordId(Long recordId);
}