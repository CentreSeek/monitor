/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: TemperatureBoundVO
 * Author:   CentreS
 * Date:     2019/9/23 16:35
 * Description: 体温监测规则
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 体温监测规则
 * @author CentreS
 * @create 2019/9/23
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "体温监测规则")
public class TemperatureBoundVO {

    @ApiModelProperty(value = "科室id(默认规则departmentId为0)")
    private Integer departmentId;
    @ApiModelProperty(value = "低温（低温阈值上界，包含临界点）")
    private Double lowTemperature;
    @ApiModelProperty(value = "正常")
    private Double normalTemperature;
    @ApiModelProperty(value = "高温")
    private Double highTemperature;
    @ApiModelProperty(value = "高低温预警：0 开，1 关")
    private Integer temperatureAlert;
    @ApiModelProperty(value = "低温预警阈值")
    private Double lowAlert;
    @ApiModelProperty(value = "高温预警阈值")
    private Double highAlert;
    @ApiModelProperty(value = "下拉列表")
    private Double[] list;
    @ApiModelProperty(value = "规则类型：default 默认规则 department 科室规则")
    private String type;
}
