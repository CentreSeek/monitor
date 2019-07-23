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


import javax.xml.crypto.Data;
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
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        return ft.format(date);
    }


}
