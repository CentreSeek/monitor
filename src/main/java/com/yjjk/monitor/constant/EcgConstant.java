/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: EcgConstant
 * Author:   CentreS
 * Date:     2019/11/13 10:12
 * Description: 心电心率
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.constant;

/**
 * @Description: 心电心率
 * @author CentreS
 * @create 2019/11/13
 */
public class EcgConstant {

    /** time */
    public static final long FIVE_MINUTES_LONG = 1000*60*5;

    /** heart rate */
    public static final int HEART_RATE_LOW = 50;
    public static final int HEART_RATE_HEIGHT = 100;
    /** breath rate */
    public static final double RESPIRATORY_RATE_LOW = 12;
    public static final double RESPIRATORY_RATE_HEIGHT = 24;

    /** 心电设备连接接口 */
    public static final String ECG_CONNECTION_URL = "http://192.168.31.165:8084/web/isSMPConnectNotice";



}
