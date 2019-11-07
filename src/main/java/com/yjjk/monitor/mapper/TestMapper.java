/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: TestMapper
 * Author:   CentreS
 * Date:     2019/10/30 15:42
 * Description: 测试类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.mapper;

import org.apache.ibatis.annotations.Insert;

import java.util.Map;

/**
 * @Description: 测试类
 * @author CentreS
 * @create 2019/10/30
 */
public interface TestMapper {

    @Insert("insert into zs_temperature_info (machine_num,temperature,pattery,temperature_status) values " +
            "(#{machine_num,jdbcType=VARCHAR},#{temperature,jdbcType=VARCHAR},#{pattery,jdbcType=INTEGER},#{temperature_status,jdbcType=VARCHAR})")
    public int insertTemperature(Map<String, Object> paramMap);

}
