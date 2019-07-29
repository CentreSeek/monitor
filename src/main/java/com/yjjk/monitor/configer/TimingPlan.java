/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: SaticScheduleTask
 * Author:   CentreS
 * Date:     2019-06-21 17:55
 * Description: 定时批量过期预约
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.configer;

import com.yjjk.monitor.controller.BaseController;
import com.yjjk.monitor.utility.DateUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.xml.transform.Source;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Configuration      // 1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling /** 2.开启定时任务 */
/**
 * @author CentreS
 * @Description: 定时批量过期预约
 * @create 2019-06-21
 */
public class TimingPlan extends BaseController {

    /**
     * 定时清理过期预约
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    private void configureTasks() {
        String date = DateUtil.getOneMonthAgo();
        int i = super.hospitalService.temperatureInfoTask(date);
        System.err.println("执行预约过期定时计划     时间: " + date + "   执行条数:" + i);
    }

}
