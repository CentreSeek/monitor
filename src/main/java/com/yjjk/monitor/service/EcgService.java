/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: ECGService
 * Author:   CentreS
 * Date:     2019/11/13 10:04
 * Description: 心电模块
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service;

import com.yjjk.monitor.controller.BaseController;
import com.yjjk.monitor.entity.ZsPatientRecord;
import com.yjjk.monitor.entity.json.HealthHistory;
import com.yjjk.monitor.entity.transaction.BackgroundResult;
import com.yjjk.monitor.entity.vo.EcgMonitorVO;
import com.yjjk.monitor.entity.vo.HealthHistoryVO;
import com.yjjk.monitor.entity.vo.UseMachineVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author CentreS
 * @Description: 心电模块
 * @create 2019/11/13
 */
public interface EcgService {

    /**
     * 获取监控列表
     *
     * @param departmentId
     * @return
     */
    List<UseMachineVO> getMonitorsInfo(Integer departmentId);

    List<EcgMonitorVO> updateUseMachine(List<UseMachineVO> monitorsInfo, Integer departmentId);

    List<HealthHistory> getHealthHistory(Long paramLong);

    HealthHistoryVO parseRateHistory(List<HealthHistory> paramList, HealthHistoryVO paramHealthHistoryVO);

    boolean stopEcg(ZsPatientRecord paramZsPatientRecord) throws Exception;

    /**
     * 清理导出心电数据
     * @return
     */
    int cleanEcgExport();

    /**
     * @Description 连接心电设备
     * @param machineId
     * @param bedId
     * @param connectionType
     * @return com.yjjk.monitor.entity.transaction.BackgroundResult
     */
    BackgroundResult connectEcgMachine(Integer machineId, Integer bedId, String connectionType);
}
