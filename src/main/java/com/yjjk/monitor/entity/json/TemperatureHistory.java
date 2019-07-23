/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: TemperatureHistory
 * Author:   CentreS
 * Date:     2019/7/22 10:20
 * Description: 历史体温
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity.json;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 历史体温
 * @author CentreS
 * @create 2019/7/22
 */
@Data
@Accessors(chain = true)
public class TemperatureHistory {

    private String temperature;
    private String dateTime;
}
