/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: ZsManagerInfo
 * Author:   CentreS
 * Date:     2019/7/17 13:50
 * Description: 管理员
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
 * @Description: 管理员
 * @author CentreS
 * @create 2019/7/17
 */
@Data
@Accessors(chain = true)
public class ZsManagerInfo{
    private Integer managerId;

    private String account;

    private String password;

    private String name;

    private String phone;

    private String post;

    private Integer departmentId;

    private Integer sex;

    private Integer role;

    private String loginTime;

    private String createTime;

    private Integer status;

}