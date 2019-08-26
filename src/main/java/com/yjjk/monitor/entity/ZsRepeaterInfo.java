package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class ZsRepeaterInfo implements Serializable {
    private Integer id;
    private String mac;
    private String ip;
    private Integer linkstatus;
    private Integer machineTypeId;
    private Integer departmentId;
    private Integer roomId;
    private String remark;
    private Integer status;
    private Date createTime;
    private Integer failCount;

    /**
     * 分页信息
     */
    private Integer startLine;
    private Integer pageSize;
    /**
     * 添加字段
     */
    private String repeaterName;
    private String repeaterNum;
    private String departmentName;
    private String roomName;

}