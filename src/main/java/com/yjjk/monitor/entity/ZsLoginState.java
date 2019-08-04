package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class ZsLoginState {
    private String token;

    private String ip;

    private Integer managerId;

    private String loginOut;

    private Integer status;

}