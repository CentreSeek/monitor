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

import com.alibaba.fastjson.JSONArray;
import com.yjjk.monitor.controller.BaseController;
import com.yjjk.monitor.entity.ZsEcgInfo;
import com.yjjk.monitor.mapper.ZsEcgInfoMapper;
import com.yjjk.monitor.mapper.ZsHealthInfoMapper;
import com.yjjk.monitor.service.EcgService;
import com.yjjk.monitor.service.HospitalService;
import com.yjjk.monitor.utility.DateUtil;
import com.yjjk.monitor.websocket.WebSocketServer;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Configuration      // 1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling /** 2.开启定时任务 */
/**
 * @author CentreS
 * @Description: 定时批量过期预约
 * @create 2019-06-21
 */
public class TimingPlan{

    protected static Logger logger = Logger.getLogger(BaseController.class);
    @Resource
    private HospitalService hospitalService;
    @Resource
    private ZsEcgInfoMapper zsEcgInfoMapper;
    @Resource
    private ZsHealthInfoMapper zsHealthInfoMapper;
    @Resource
    private EcgService ecgService;
    /**
     * 定时计划：1.清理过期预约
     */
    @Scheduled(cron = "0 0 0 1/15 * ?")
    private void configureTasks() {
        String date = DateUtil.getOneMonthAgo();
        int i = hospitalService.temperatureInfoTask(date);
        int j = zsHealthInfoMapper.healthInfoTask(date);
        logger.info("执行体温定时计划     时间: " + date + "   执行条数:" + i);
        logger.info("执行心率定时计划     时间: " + date + "   执行条数:" + j);
    }

    /**
     * 实时心电数据推送
     */
    @Scheduled(cron = "*/1 * * * * ?")
    private void pushEcgInfo() {
        CopyOnWriteArraySet<WebSocketServer> webSocketSet =
                WebSocketServer.getWebSocketSet();
        int i = 0 ;
        webSocketSet.forEach(c->{
            try {
                Map<String,Object> paraMap = new HashMap<>();
                paraMap.put("machineId",c.getMachineId());
                paraMap.put("timeStamp",DateUtil.getCurrentTimeLong());
                ZsEcgInfo newEcg = zsEcgInfoMapper.getNewEcg(paraMap);
                String s = JSONArray.toJSONString(newEcg);
                c.sendMessage(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 心电数据清除计划
     */
    @Scheduled(cron = "0 0 0 1/7 * ?")
    private void cleanEcgExport() {
        String date = DateUtil.getCurrentTime();
        int j = ecgService.cleanEcgExport();
        logger.info("执行心电数据清理计划     时间: " + date + "   执行条数:" + j);
    }
//    @Scheduled(cron = "0 0 0 * * ?")
//    private void configureTimerCountTasks() {
//        TimerCount.resetHistoryExportCount();
//    }

}
