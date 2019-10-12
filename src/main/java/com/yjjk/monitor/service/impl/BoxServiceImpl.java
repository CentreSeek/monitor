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

import java.util.Iterator;
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
        Iterator<ZsBoxInfo> iter = zsBoxesInfo.iterator();
        for (int i = 0; i < monitorsInfo.size(); i++) {
            String machineNum = monitorsInfo.get(i).getMachineNum();
            if (monitorsInfo.get(i).getRecordId() != null) {
                monitorsInfo.get(i).setBoxBatteryStatus(BoxConstant.BOX_STATUS_NORMAL);
                while (iter.hasNext()) {
                    ZsBoxInfo next = iter.next();
                    if (machineNum == next.getMachineNum()) {
                        monitorsInfo.get(i).setBoxBatteryStatus(next.getBoxBatteryStatus());
                        iter.remove();
                        break;
                    }
                }
            }
        }
        return monitorsInfo;
    }
}
