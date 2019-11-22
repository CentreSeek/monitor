package com.yjjk.monitor.entity.transaction;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: monitor
 * @description: 邵子城-心电设备连接
 * @author: CentreS
 * @create: 2019-11-21 16:51:31
 **/
@Data
@Accessors(chain = true)
public class BackgroundSend {
    public static final String DATA_CONNECTION = "0";
    public static final String DATA_LOSE_CONNECTION = "1";
    private String deviceId;
    private String actionId;
    private String data;
}
