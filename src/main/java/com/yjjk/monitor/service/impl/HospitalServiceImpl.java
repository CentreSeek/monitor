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

import com.yjjk.monitor.entity.ZsBedInfo;
import com.yjjk.monitor.entity.ZsDepartmentInfo;
import com.yjjk.monitor.entity.ZsRoomInfo;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.HospitalService;
import com.yjjk.monitor.utility.ExcelUtils;
import com.yjjk.monitor.utility.FileUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 医院信息
 * @author CentreS
 * @create 2019/7/18
 */
@Service
public class HospitalServiceImpl extends BaseService implements HospitalService {

    @Override
    public List<ZsDepartmentInfo> selectDetail(Integer departmentId)
    {
        return this.ZsDepartmentInfoMapper.selectDetail(departmentId);
    }
    @Override
    public int temperatureInfoTask(String dateOfOneMonthAgo)
    {
        temperatureInfoPersistent(dateOfOneMonthAgo);
        return this.zsTemperatureInfoMapper.temperatureInfoTask(dateOfOneMonthAgo);
    }
    @Override
    public int healthInfoTask(String dateOfOneMonthAgo)
    {
        healthInfoPersistent(dateOfOneMonthAgo);
        return this.zsHealthInfoMapper.healthInfoTask(dateOfOneMonthAgo);
    }
    @Override
    public int temperatureInfoPersistent(String dateOfOneMonthAgo)
    {
        List<String> exportTemperatures = this.zsTemperatureInfoMapper.getExportTemperatures(dateOfOneMonthAgo);
        ExcelUtils.writeToTxt(exportTemperatures, FileUtils.getRootPath() + "\\ExportData\\TemperatureExport");
        return exportTemperatures.size();
    }
    @Override
    public int healthInfoPersistent(String dateOfOneMonthAgo)
    {
        List<String> list = this.zsHealthInfoMapper.getExportHealth(dateOfOneMonthAgo);
        ExcelUtils.writeToTxt(list, FileUtils.getRootPath() + "\\ExportData\\HealthExport");
        return list.size();
    }
    @Override
    public List<ZsDepartmentInfo> selectDepartments()
    {
        return this.ZsDepartmentInfoMapper.selectDepartments();
    }
    @Override
    public List<ZsRoomInfo> selectRooms(Integer departmentId)
    {
        return this.ZsRoomInfoMapper.selectRooms(departmentId);
    }
    @Override
    public List<ZsBedInfo> selectEmptyBeds(Map<String, Object> paraMap)
    {
        return this.ZsBedInfoMapper.selectEmptyBeds(paraMap);
    }

}
