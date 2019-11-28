/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: HealthHistoryVO
 * Author:   CentreS
 * Date:     2019/11/19 16:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity.vo;

import com.yjjk.monitor.entity.json.HealthHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description:
 * @author CentreS
 * @create 2019/11/19
 */
@Data
@Accessors(chain = true)
public class HealthHistoryVO {
    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;
    @ApiModelProperty("使用时间")
    private String useTimes;
    @ApiModelProperty("心率最大值")
    private Double highestHeartRate;
    @ApiModelProperty("心率最小值")
    private Double lowestHeartRate;
    @ApiModelProperty("心率平均值")
    private Double avgHeartRate;

    @ApiModelProperty("呼吸率最大值")
    private Double highestRespiratoryRate;
    @ApiModelProperty("呼吸率最小值")
    private Double lowestRespiratoryRate;
    @ApiModelProperty("呼吸率平均值")
    private Double avgRespiratoryRate;

    @ApiModelProperty("心率呼吸率")
    private List<HealthHistory> list;
}
