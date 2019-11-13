package com.yjjk.monitor.controller;


import com.yjjk.monitor.mapper.TestMapper;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TemperatureControllerTest {

    @Resource
    TestMapper testMapper;

    @Test
    void insertTemperature() {
        int nums = 50 * 100 * 30 * 24 * 60;
        for (int i = 0; i < nums; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("machine_num", getMachineNum(i));
            map.put("temperature", "38.0");
            map.put("pattery", 100);
            map.put("temperature_status", "BLUE");
            testMapper.insertTemperature(map);
        }

    }

    public String getMachineNum(Integer i){
        String num = String.valueOf(i%5000);
        if (num.length() ==1){
            return "SBS/0000000" + num;
        }else if (num.length() ==2){
            return "SBS/000000" + num;
        }else if (num.length() ==3){
            return "SBS/00000" + num;
        }else if (num.length() ==4){
            return "SBS/0000" + num;
        }else {
            return "SBS/00005495";
        }

    }

}