/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: BaseService
 * Author:   CentreS
 * Date:     2019-06-20 16:34
 * Description: base service
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service;


import com.yjjk.monitor.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author CentreS
 * @Description: base service
 * @create 2019-06-20
 */
public class BaseService {
    protected static Logger logger = LoggerFactory.getLogger(BaseService.class);
    @Resource
    protected ZsBedInfoMapper ZsBedInfoMapper;
    @Resource
    protected ZsDepartmentInfoMapper ZsDepartmentInfoMapper;
    @Resource
    protected ZsMachineInfoMapper ZsMachineInfoMapper;
    @Resource
    protected ZsManagerInfoMapper ZsManagerInfoMapper;
    @Resource
    protected ZsPatientInfoMapper ZsPatientInfoMapper;
    @Resource
    protected ZsPatientRecordMapper ZsPatientRecordMapper;
    @Resource
    protected ZsRoomInfoMapper ZsRoomInfoMapper;
    @Resource
    protected ZsTemperatureInfoMapper zsTemperatureInfoMapper;
    @Resource
    protected ZsLoginStateMapper zsLoginStateMapper;
    @Resource
    protected ZsMachineTypeInfoMapper zsMachineTypeInfoMapper;
    @Resource
    protected ZsRepeaterInfoMapper zsRepeaterInfoMapper;
    @Resource
    protected ZsTemperatureBoundMapper zsTemperatureBoundMapper;
    @Resource
    protected ZsBoxInfoMapper zsBoxInfoMapper;


}
