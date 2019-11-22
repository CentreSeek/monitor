/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: RepeaterServiceImpl
 * Author:   CentreS
 * Date:     2019/8/23 9:57
 * Description: 中继器管理模块
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service.impl;

import com.yjjk.monitor.entity.ZsMachineTypeInfo;
import com.yjjk.monitor.entity.ZsRepeaterInfo;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.RepeaterService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 中继器管理模块
 * @author CentreS
 * @create 2019/8/23
 */
@Service
public class RepeaterServiceImpl extends BaseService implements RepeaterService {

    @Override
    public List<ZsMachineTypeInfo> selectMachineTypes() {
        return super.zsMachineTypeInfoMapper.selectRepeaterTypes();
    }

    @Override
    public List<ZsMachineTypeInfo> selectMachineNums(Integer id) {
        return super.zsMachineTypeInfoMapper.selectRepeaterNums(id);
    }

    @Override
    public int insertSelective(ZsRepeaterInfo repeater) {
        return super.zsRepeaterInfoMapper.insertSelective(repeater);
    }

    @Override
    public int startRepeater(ZsRepeaterInfo repeaterInfo) {
        return super.zsRepeaterInfoMapper.updateByPrimaryKeySelective(repeaterInfo.setLinkstatus(1).setRemark("").setFailCount(0));
    }

    @Override
    public int stopRepeater(Integer id,String remark) {
        return super.zsRepeaterInfoMapper.updateByPrimaryKeySelective(new ZsRepeaterInfo().setLinkstatus(2).setId(id).setRemark(remark).setFailCount(0));
    }

    @Override
    public List<ZsRepeaterInfo> selectRepeaters(ZsRepeaterInfo record) {
        return super.zsRepeaterInfoMapper.selectRepeaters(record);
    }

    @Override
    public int selectRepeaterCount(ZsRepeaterInfo record) {
        return super.zsRepeaterInfoMapper.selectRepeaterCount(record);
    }

    @Override
    public boolean isExistRepeater(ZsRepeaterInfo zsRepeaterInfo) {
        if (super.zsRepeaterInfoMapper.isExistRepeater(zsRepeaterInfo) > 0){
            return true;
        }
        return false;
    }
}
