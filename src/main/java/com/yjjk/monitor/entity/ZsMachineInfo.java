/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: ZsMachineInfo
 * Author:   CentreS
 * Date:     2019/7/17 13:50
 * Description: 智能温度贴
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description: 智能温度贴
 * @author CentreS
 * @create 2019/7/17
 */
@Data
@Accessors(chain = true)
public class ZsMachineInfo  {
    private Integer machineId;
    private String name;
    private String machineModel;
    private String machineNum;
    private Integer departmentId;
    private Integer usageState;
    private String remark;
    private String createTime;
    private Integer status;

    /** 批量导入：设备编号 */
    private List<String> machineNums;
    /** 分页信息 */
    private Integer startLine;
    private Integer pageSize;
    private String departmentName;
}