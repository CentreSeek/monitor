/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: UseMachine
 * Author:   CentreS
 * Date:     2019/7/19 9:39
 * Description: 启用设备
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 启用设备
 * @author CentreS
 * @create 2019/7/19
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "监控数据")
public class UseMachineVO {
    /** patientInfo */
    @ApiModelProperty(value = "patientInfo:病人名")
    private String patientName;
    @ApiModelProperty(value = "patientInfo:病历号")
    private String caseNum;

    /** 病房信息 */
    @ApiModelProperty(value = "病房信息:科室名称")
    private String departmentName;
    @ApiModelProperty(value = "病房信息:病房名")
    private String roomName;
    @ApiModelProperty(value = "病房信息:病床名")
    private String bedName;
    @ApiModelProperty(value = "病房信息:病床ID")
    private Integer bedId;

    /** record */
    @ApiModelProperty(value = "record:病单ID")
    private Long recordId;
    @ApiModelProperty(value = "record:设备ID")
    private Integer machineId;
    @ApiModelProperty(value = "record:使用状态 0：预热中 1：使用中 2：连接异常")
    private Integer recordState;
    @ApiModelProperty(value = "record:启用时间")
    private String startTime;
    @ApiModelProperty(value = "record:结束时间")
    private String endTime;

    /** machine */
    @ApiModelProperty(value = "machine：设备SN码")
    private String machineNum;
    @ApiModelProperty(value = "machine:设备号")
    private String machineNo;


    /** temperature */
    @ApiModelProperty(value = "temperature：体温")
    private String temperature;
    @ApiModelProperty(value = "temperature：设备电量")
    private Integer pattery;
    @ApiModelProperty(value = "temperature：状态")
    private String temperatureStatus;
    @ApiModelProperty(value = "temperature：高低温预警 0：正常 1：低温 2：高温")
    private Integer temperatureAlert;
    @ApiModelProperty(value = "temperature：时间戳")
    private Long timestamp;
    @ApiModelProperty(value = "temperature：使用时间")
    private String useTimes;


    /** box */
    @ApiModelProperty(value = "box：体温贴盒子信息 LOW：低电量 NORMAL：正常")
    private String boxBatteryStatus;

}
