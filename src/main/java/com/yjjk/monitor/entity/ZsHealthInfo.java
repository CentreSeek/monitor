package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class ZsHealthInfo {
    private Integer temperatureId;

    private String machineId;

    private BigDecimal heartRate;

    private BigDecimal respiratoryRate;

    private Date createTime;

    private Long timestamp;

}