/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: EcgHistory
 * Author:   CentreS
 * Date:     2019/11/19 16:32
 * Description: Ecg历史记录
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity.json;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: Ecg历史记录
 * @author CentreS
 * @create 2019/11/19
 */
@Data
@Accessors(chain = true)
public class EcgHistory {
    private String ecg;
    private Long dateTime;
}
