/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: ZsPatientInfo
 * Author:   CentreS
 * Date:     2019/7/17 13:50
 * Description: 病人
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 病人
 * @author CentreS
 * @create 2019/7/17
 */
@Data
@Accessors(chain = true)
public class ZsPatientInfo{
    private Integer patientId;
    private String name;
    private String caseNum;
    private Integer departmentId;
    private Integer roomId;
    private Integer bedId;
    private String createTime;
    private Integer status;
}