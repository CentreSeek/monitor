/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: DateUtil
 * Author:   CentreS
 * Date:     2019-06-21 16:02
 * Description: 时间工具
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.utility;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 时间工具
 * @author CentreS
 * @create 2019-06-21
 */
public class DateUtil {

    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        // 获得一个日历
        Calendar cal = Calendar.getInstance();
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 指示一个星期中的某天。
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekDays[w];
    }

    public static String getRecordId(){
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMdd");
        return ft.format(date);
    }

    public static String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        return ft.format(date);
    }

    public static String getDatePoor(String startTime) {
        return getDatePoor(startTime, getCurrentTime());
    }
    public static String getDatePoor(String startTime, String endTime) {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = ft.parse(startTime);
            endDate = ft.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return hour + "小时" + min + "分钟";
    }

    /**
     * 获取一个月前的日期
     * @return
     */
    public static String getOneMonthAgo() {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return ft.format(cal.getTime());
    }

}
