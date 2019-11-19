package com.yjjk.monitor.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ZsHealthInfo {
    private Integer temperatureId;
    private String machineId;
    @ApiModelProperty("心率")
    private BigDecimal heartRate;
    @ApiModelProperty("呼吸率")
    private BigDecimal respiratoryRate;
    @ApiModelProperty("时间")
    private String createTime;
    private Long timestamp;

}