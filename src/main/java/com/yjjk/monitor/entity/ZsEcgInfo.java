package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class ZsEcgInfo implements Serializable {
    private Integer temperatureId;

    private String machineId;

    private Date createTime;

    private Long timestamp;

    private String ecg;

}