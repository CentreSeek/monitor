/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: TemperatureBound
 * Author:   CentreS
 * Date:     2019/9/23 15:06
 * Description: 温度监测规则设置
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author CentreS
 * @Description: 温度监测规则设置
 * @create 2019/9/23
 */
@Data
@Accessors(chain = true)
public class TemperatureBound {

    @NotNull
    @ApiParam(value = "科室id", required = true)
    private Integer departmentId;
    @NotNull
    @ApiParam(value = "低温", required = true)
    private Double lowTemperature;
    @NotNull
    @ApiParam(value = "正常", required = true)
    private Double normalTemperature;
    @NotNull
    @ApiParam(value = "高温", required = true)
    private Double highTemperature;
    @NotNull
    @ApiParam(value = "高低温预警：0 开，1 关", required = true)
    private Integer temperatureAlert;
    @NotNull
    @ApiParam(value = "低温预警阈值", required = true)
    private Double lowAlert;
    @NotNull
    @ApiParam(value = "高温预警阈值", required = true)
    private Double highAlert;

}
