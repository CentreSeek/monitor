/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: HealthHistoryExport
 * Author:   CentreS
 * Date:     2019/11/19 16:27
 * Description: 心率历史记录导出
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity.export;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 心率历史记录导出
 * @author CentreS
 * @create 2019/11/19
 */
@Data
@Accessors(chain = true)
public class HealthHistoryExport {

    private String heartRate;
    private String respiratoryRate;
    private Long dateTime;
}
