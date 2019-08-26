/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: HospitalServiceImpl
 * Author:   CentreS
 * Date:     2019/7/18 17:19
 * Description: 医院信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service.impl;

import com.yjjk.monitor.entity.ZsDepartmentInfo;
import com.yjjk.monitor.entity.ZsRoomInfo;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.HospitalService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 医院信息
 * @author CentreS
 * @create 2019/7/18
 */
@Service
public class HospitalServiceImpl extends BaseService implements HospitalService {


    @Override
    public List<ZsDepartmentInfo> selectDetail(Integer departmentId) {
        return super.ZsDepartmentInfoMapper.selectDetail(departmentId);
    }

    @Override
    public int temperatureInfoTask(String dateOfOneMonthAgo) {
        return super.zsTemperatureInfoMapper.temperatureInfoTask(dateOfOneMonthAgo);
    }

    @Override
    public List<ZsDepartmentInfo> selectDepartments() {
        return super.ZsDepartmentInfoMapper.selectDepartments();
    }

    @Override
    public List<ZsRoomInfo> selectRooms(Integer departmentId) {
        return super.ZsRoomInfoMapper.selectRooms(departmentId);
    }
}
