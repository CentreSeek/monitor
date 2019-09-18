/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: MonitorRecord
 * Author:   CentreS
 * Date:     2019/8/4 14:32
 * Description: 温度监测
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.constant;

import org.springframework.stereotype.Component;

/**
 * @Description: 温度监测
 * @author CentreS
 * @create 2019/8/4
 */
public class MonitorRecord {

    /** 预热 */
    public static final int RECORD_STATE_READY = 0;
    /** 使用中 */
    public static final int RECORD_STATE_USAGE = 1;
    /** 连接异常 */
    public static final int RECORD_STATE_ERR = 2;
    /** 未使用 */
    public static final int RECORD_STATE_UNUSED = 3;
    /** 未佩戴 */
    public static final int RECORD_STATE_ERR_WEAR = 4;
    /** 未按规范黏贴 */
    public static final int RECORD_STATE_ERR_USED = 5;

}
