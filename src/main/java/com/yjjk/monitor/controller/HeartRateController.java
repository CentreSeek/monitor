/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: HeartRateController
 * Author:   CentreS
 * Date:     2019/11/11 9:32
 * Description: 心率模块
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.controller;

import com.yjjk.monitor.configer.CommonResult;
import com.yjjk.monitor.entity.ZsManagerInfo;
import com.yjjk.monitor.entity.vo.EcgMonitorVO;
import com.yjjk.monitor.entity.vo.UseMachineVO;
import com.yjjk.monitor.utility.ResultUtil;
import com.yjjk.monitor.utility.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 心率模块
 * @author CentreS
 * @create 2019/11/11
 */
@Api(tags = {"心率模块"})
@RestController
@RequestMapping("heart")
public class HeartRateController extends BaseController{


    private static final Logger LOGGER = LoggerFactory.getLogger(HeartRateController.class);

    /**
     * 心电监测信息
     */
    @ApiOperation("心电监测信息")
    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public CommonResult<List<EcgMonitorVO>> getMinitors(@ApiParam(value = "管理员id", required = true) @RequestParam(value = "managerId") Integer managerId,
                                                        @ApiParam(value = "使用中设备0：使用中 1：未使用") @RequestParam(value = "used", required = false) Integer used,
                                                        @ApiParam(value = "起始床位id") @RequestParam(value = "start", required = false) Integer start,
                                                        @ApiParam(value = "结束床位id") @RequestParam(value = "end", required = false) Integer end) {
        /********************** 参数初始化 **********************/
        ZsManagerInfo managerInfo = super.managerService.getManagerInfo(managerId);
        Integer departmentId = null;
        if (managerInfo.getRole() == 2) {
            departmentId = managerInfo.getDepartmentId();
        }
        // 监控信息
        List<UseMachineVO> monitorsInfo = super.ecgService.getMonitorsInfo(departmentId);
        // 根据病床id筛选监控信息
        monitorsInfo = super.patientRecordService.selectiveByBedId(monitorsInfo, start == null ? 0 : start, end == null ? Integer.MAX_VALUE : end);
        // 设备是否为使用中设备
        if (used != null && used == 0) {
            monitorsInfo = super.patientRecordService.isUsed(monitorsInfo);
        }
        // 姓名隐私
        for (int i = 0; i < monitorsInfo.size(); i++) {
            monitorsInfo.get(i).setPatientName(StringUtils.replaceNameX(monitorsInfo.get(i).getPatientName()));
        }
        // 设置体温规则
        List<EcgMonitorVO> list = super.ecgService.updateUseMachine(monitorsInfo, departmentId);
        return ResultUtil.returnSuccess(list);
    }

}
