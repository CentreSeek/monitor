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
public class BackgroundResult {
    private String success;
    private String code;
    private String total;
    private String message;
    private String result;
}
