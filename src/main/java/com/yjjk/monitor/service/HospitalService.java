/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: HospitalService
 * Author:   CentreS
 * Date:     2019/7/18 17:19
 * Description: 医院信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service;

import com.yjjk.monitor.entity.ZsDepartmentInfo;

import java.util.List;

/**
 * @Description: 医院信息
 * @author CentreS
 * @create 2019/7/18
 */
public interface HospitalService {

    /**
     * 查询医院信息详情
     * @param departmentId
     * @return
     */
    List<ZsDepartmentInfo> selectDetail(Integer departmentId);

    /**
     * delete---定期清理temperatureInfo表数据
     * @param dateOfOneMonthAgo
     * @return
     */
    int temperatureInfoTask(String dateOfOneMonthAgo);
}
