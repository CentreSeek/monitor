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



import javax.annotation.Resource;
import javax.rmi.PortableRemoteObject;

import com.yjjk.monitor.entity.json.TemperatureHistory;
import com.yjjk.monitor.mapper.*;
import org.apache.log4j.Logger;

/**
 * @author CentreS
 * @Description: base service
 * @create 2019-06-20
 */
public class BaseService {
    protected static Logger logger = Logger.getLogger(BaseService.class);
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

}
