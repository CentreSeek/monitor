/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: PatientTemperature
 * Author:   CentreS
 * Date:     2019/7/22 16:47
 * Description: 监控病人体温
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 监控病人体温
 * @author CentreS
 * @create 2019/7/22
 */
@Data
@Accessors(chain = true)
public class PatientTemperature {

    /** temperature */
    private Integer machineId;
    private String temperature;
    private Integer pattery;
    private String temperatureStatus;
    private String useTimes;
    private Integer timestamp;

}
