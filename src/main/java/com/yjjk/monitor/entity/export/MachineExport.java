/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: MachineExport
 * Author:   CentreS
 * Date:     2019/9/24 18:14
 * Description: 设备导出
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity.export;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author CentreS
 * @Description: 设备导出
 * @create 2019/9/24
 */
@Data
@Accessors(chain = true)
public class MachineExport {
    private String machineNo;
    private String name;
    private String machineModel;
    private String machineNum;
    private String departmentName;
    /**
     * 0：正常 1：停用 2：使用中
     */
    private Integer usageState;
    private String createTime;
    private String remark;

    public MachineExportVO transBean(MachineExport bean) {
        String usageStateString = "";
        if (bean.usageState == 0 || bean.usageState == 2) {
            usageStateString = "正常";
        } else if (bean.usageState == 1) {
            usageStateString = "停用";
        }
        MachineExportVO transBean = new MachineExportVO();
        transBean.setMachineNo(this.machineNo).setName(this.name).setMachineModel(this.machineModel).setMachineNum(this.machineNum).setDepartmentName(this.departmentName).setUsageState(usageStateString).setCreateTime(this.createTime).setRemark(this.remark);
        return transBean;
    }
}
