/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: BoxService
 * Author:   CentreS
 * Date:     2019/10/12 10:53
 * Description: 体温贴盒子
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.service;

import com.yjjk.monitor.entity.vo.UseMachineVO;

import java.util.List;

/**
 * @Description: 体温贴盒子
 * @author CentreS
 * @create 2019/10/12
 */
public interface BoxService {

    /**
     * 设置体温贴盒子信息
     * @param monitorsInfo
     * @return
     */
    List<UseMachineVO> setBoxesInfo(List<UseMachineVO> monitorsInfo);

}
