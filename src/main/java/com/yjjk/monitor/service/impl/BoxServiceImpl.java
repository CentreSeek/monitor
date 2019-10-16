/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: BoxServiceImpl
 * Author:   CentreS
 * Date:     2019/10/12 10:54
 * Description: 体温贴盒子
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service.impl;

import com.yjjk.monitor.constant.BoxConstant;
import com.yjjk.monitor.entity.ZsBoxInfo;
import com.yjjk.monitor.entity.vo.UseMachineVO;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.BoxService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CentreS
 * @Description: 体温贴盒子
 * @create 2019/10/12
 */
@Service
public class BoxServiceImpl extends BaseService implements BoxService {

    @Override
    public List<UseMachineVO> setBoxesInfo(List<UseMachineVO> monitorsInfo) {
        List<ZsBoxInfo> zsBoxesInfo = super.zsBoxInfoMapper.selectAll();
        for (int i = 0; i < monitorsInfo.size(); i++) {
            String machineNum = monitorsInfo.get(i).getMachineNum();
            if (monitorsInfo.get(i).getRecordId() != null) {
                monitorsInfo.get(i).setBoxBatteryStatus(BoxConstant.BOX_STATUS_NORMAL);
                for (ZsBoxInfo iter : zsBoxesInfo) {
                    if (machineNum.equals(iter.getMachineNum())) {
                        monitorsInfo.get(i).setBoxBatteryStatus(iter.getBoxBatteryStatus());
                        break;
                    }
                }
            }
        }
        return monitorsInfo;
    }
}
