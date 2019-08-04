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

    public static final int RECORD_STATE_READY = 0;
    public static final int RECORD_STATE_USAGE = 1;
    public static final int RECORD_STATE_ERR = 2;
    public static final int RECORD_STATE_UNUSED = 3;


}
