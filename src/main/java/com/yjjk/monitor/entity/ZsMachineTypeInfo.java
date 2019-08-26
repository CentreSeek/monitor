package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class ZsMachineTypeInfo {
    private Integer id;

    private String name;

    private Integer level;

    private Integer type;

    private Integer parentId;

    private Date createTime;

    private Integer status;

}