/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: TemperatureAlertServiceImpl
 * Author:   CentreS
 * Date:     2019/9/24 9:24
 * Description: 体温预警规则
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service.impl;

import com.yjjk.monitor.constant.TemperatureConstant;
import com.yjjk.monitor.entity.ZsTemperatureBound;
import com.yjjk.monitor.entity.param.TemperatureBound;
import com.yjjk.monitor.entity.vo.TemperatureBoundVO;
import com.yjjk.monitor.entity.vo.UseMachineVO;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.TemperatureBoundService;
import com.yjjk.monitor.utility.DateUtil;
import com.yjjk.monitor.utility.ReflectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CentreS
 * @Description: 体温预警规则
 * @create 2019/9/24
 */
@Service
public class TemperatureBoundServiceImpl extends BaseService implements TemperatureBoundService {

    @Override
    public List<TemperatureBoundVO> getDefaultAlert(Integer departmentId) {
        List<TemperatureBoundVO> list = new ArrayList<>();
        ZsTemperatureBound defaultTemperature = super.zsTemperatureBoundMapper.selectByPrimaryKey(TemperatureConstant.DEFAULT_DEPARTMENT_ID);
        list.add(ReflectUtils.transformToBean(defaultTemperature,
                TemperatureBoundVO.class).setList(TemperatureConstant.TEMPERATURE_LIST).setType(TemperatureConstant.ALERT_TYPE_DEFAULT));
        ZsTemperatureBound departmentTemperature = super.zsTemperatureBoundMapper.selectByPrimaryKey(departmentId);
        if (departmentTemperature == null) {
            departmentTemperature = defaultTemperature;
        }
        list.add(ReflectUtils.transformToBean(departmentTemperature, TemperatureBoundVO.class).setList(TemperatureConstant.TEMPERATURE_LIST).setType(TemperatureConstant.ALERT_TYPE_DEPARTMENT));
        return list;
    }

    @Override
    public Integer setTemperatureBound(TemperatureBound param) {
        ZsTemperatureBound zsTemperatureBound =
                super.zsTemperatureBoundMapper.selectByPrimaryKey(param.getDepartmentId());
        int i = 0;
        if (zsTemperatureBound != null) {
            i = super.zsTemperatureBoundMapper.updateByPrimaryKeySelective(ReflectUtils.transformToBean(param,
                    ZsTemperatureBound.class).setChangeTime(DateUtil.getCurrentTime()));
        } else {
            i = super.zsTemperatureBoundMapper.insertSelective(ReflectUtils.transformToBean(param,
                    ZsTemperatureBound.class));
        }
        return i;
    }

    @Override
    public List<UseMachineVO> updateUseMachine(List<UseMachineVO> monitorsInfo, Integer departmentId) {
        ZsTemperatureBound temperatureBound = super.zsTemperatureBoundMapper.selectByPrimaryKey(departmentId);
        if (temperatureBound == null) {
            // 获取默认规则
            temperatureBound = super.zsTemperatureBoundMapper.selectByPrimaryKey(TemperatureConstant.DEFAULT_DEPARTMENT_ID);
        }
        for (int i = 0; i < monitorsInfo.size(); i++) {
            if (monitorsInfo.get(i).getRecordId() != null) {
                Double temperature = Double.parseDouble(monitorsInfo.get(i).getTemperature());
                /** 设置体温状态 */
                if (temperature <= temperatureBound.getLowTemperature()) {
                    monitorsInfo.get(i).setTemperatureStatus(TemperatureConstant.LOW_TEMPERATURE);
                } else if (temperature <= temperatureBound.getNormalTemperature()) {
                    monitorsInfo.get(i).setTemperatureStatus(TemperatureConstant.NORMAL_TEMPERATURE);
                } else if (temperature <= temperatureBound.getHighTemperature()) {
                    monitorsInfo.get(i).setTemperatureStatus(TemperatureConstant.HIGHER_TEMPERATURE);
                } else if (temperature > temperatureBound.getHighTemperature()) {
                    monitorsInfo.get(i).setTemperatureStatus(TemperatureConstant.HIGH_TEMPERATURE);
                }
                /** 设置体温预警 */
                if (temperatureBound.getTemperatureAlert().equals(TemperatureConstant.ALERT_STATUS_CLOSE)){
                    break;
                }
                if (temperature <= temperatureBound.getLowAlert()) {
                    monitorsInfo.get(i).setTemperatureAlert(TemperatureConstant.TEMPERATURE_ALERT_LOW);
                } else if (temperature >= temperatureBound.getHighAlert()) {
                    monitorsInfo.get(i).setTemperatureAlert(TemperatureConstant.TEMPERATURE_ALERT_HIGH);
                } else {
                    monitorsInfo.get(i).setTemperatureAlert(TemperatureConstant.TEMPERATURE_ALERT_NORMAL);
                }
            }
        }
        return monitorsInfo;
    }
}
