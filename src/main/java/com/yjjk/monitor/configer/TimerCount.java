/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: TimerCount
 * Author:   CentreS
 * Date:     2019/9/19 11:14
 * Description: 导出文件全局计数
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.configer;

/**
 * @Description: 导出文件全局计数
 * @author CentreS
 * @create 2019/9/19
 */
public class TimerCount {
    private static TimerCount instance = new TimerCount();
    public Integer count;
    TimerCount(){
        count = 1;
    }
    public synchronized static Integer getHistoryExportCount(){
        return instance.count++;
    }
    public static void resetHistoryExportCount(){
        instance.count = 1;
    }
}
