/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: EcgHistoryVO
 * Author:   CentreS
 * Date:     2019/11/19 16:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity.vo;

import com.yjjk.monitor.entity.ZsEcgInfo;
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
public class EcgHistoryVO {

    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;
    @ApiModelProperty("使用时间")
    private String useTimes;
    @ApiModelProperty("心电")
    private List<ZsEcgInfo> list;
}
