package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ZsBoxInfo {
    private Integer boxId;

    private String machineNum;

    private String boxBatteryStatus;

    private Date createTime;

    private Date updateTime;

}