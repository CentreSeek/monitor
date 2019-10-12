/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: TemperatureAlert
 * Author:   CentreS
 * Date:     2019/9/24 9:19
 * Description: 体温预警规则
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service;

import com.yjjk.monitor.entity.param.TemperatureBound;
import com.yjjk.monitor.entity.vo.TemperatureBoundVO;
import com.yjjk.monitor.entity.vo.UseMachineVO;

import java.util.List;

/**
 * @Description: 体温预警规则
 * @author CentreS
 * @create 2019/9/24
 */
public interface TemperatureBoundService {
    /**
     * 获取默认规则
     * @param departmentId
     * @return
     */
    List<TemperatureBoundVO> getDefaultAlert(Integer departmentId);

    /**
     * 设置体温规则
     * @param param
     * @return
     */
    Integer setTemperatureBound(TemperatureBound param);

    /**
     * 设置监控信息的体温规则
     * @param monitorsInfo
     * @return
     */
    List<UseMachineVO> updateUseMachine(List<UseMachineVO> monitorsInfo, Integer departmentId);

}
