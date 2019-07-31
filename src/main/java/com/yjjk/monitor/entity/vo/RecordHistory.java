/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: RecordHistory
 * Author:   CentreS
 * Date:     2019/7/23 10:54
 * Description: 历史记录
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author CentreS
 * @Description: 历史记录
 * @create 2019/7/23
 */
@Data
@Accessors(chain = true)
public class RecordHistory {

    private Long recordId;
    private String patientName;
    private String caseNum;
    private String departmentName;
    private String roomName;
    private String bedName;
    private String startTime;
    private String endTime;
    private Integer usageState;

    /** mappingParam */
    private Integer departmentId;
    private String startDate;
    private String endDate;
    private Integer startLine;
    private Integer pageSize;

    /** requestParam */
    private Integer currentPage;
    private Integer timestamp;

}
