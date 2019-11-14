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

import java.math.BigDecimal;

/**
 * @Description: 启用设备
 * @author CentreS
 * @create 2019/7/19
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "监控数据")
public class EcgMonitorVO {
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
    @ApiModelProperty(value = "record:使用状态 0：使用中 1：连接中断 2:未使用")
    private Integer recordState;
    @ApiModelProperty(value = "record:启用时间")
    private String startTime;
    @ApiModelProperty(value = "record:结束时间")
    private String endTime;
    @ApiModelProperty(value = "record:使用时间")
    private String useTimes;

    /** machine */
    @ApiModelProperty(value = "machine：设备SN码")
    private String machineNum;
    @ApiModelProperty(value = "machine:设备号")
    private String machineNo;
    @ApiModelProperty(value = "machine:电量")
    private String pattery;

    /** health info */
    @ApiModelProperty(value = "heartRate:心率状态 0：正常 1：异常")
    private Integer heartRateStatus;
    @ApiModelProperty(value = "respiratoryRate:呼吸率状态 0：正常 1：异常")
    private Integer respiratoryRateStatus;
    @ApiModelProperty(value = "heartRate:心率")
    private BigDecimal heartRate;
    @ApiModelProperty(value = "respiratoryRate:呼吸率")
    private BigDecimal respiratoryRate;


}
