/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: ZsPatientRecord
 * Author:   CentreS
 * Date:     2019/7/19 9:39
 * Description: 历史记录
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description: 历史记录
 * @author CentreS
 * @create 2019/7/19
 */
@Data
@Accessors(chain = true)
public class ZsPatientRecord{
    private Long recordId;
    private Integer patientId;
    private Integer machineId;
    private Integer bedId;
    private String startTime;
    private String endTime;
    private Integer usageState;
    private Date createTime;
    private Integer status;
    private String temperatureHistory;
    private String ecgHistory;
    private String rateHistory;
    private Integer recordType;

}