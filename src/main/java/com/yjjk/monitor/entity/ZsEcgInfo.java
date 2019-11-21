package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ZsEcgInfo implements Serializable {
    private Integer temperatureId;

    private String machineId;

    private String createTime;

    private Long timestamp;

    private String ecg;

}