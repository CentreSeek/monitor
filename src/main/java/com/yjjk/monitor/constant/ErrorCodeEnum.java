/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: ErrorCodeEnum
 * Author:   CentreS
 * Date:     2019/8/20 14:49
 * Description: 错误代码枚举类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.constant;

/**
 * @Description: 错误代码枚举类
 * @author CentreS
 * @create 2019/8/20
 */

/**
 * 错误代码枚举类
 */
public enum ErrorCodeEnum {

    SUCCESS("200", "success"),

    PARAM_EMPTY("301", "必选参数为空"),
    PARAM_ERROR("302", "参数格式错误"),
    TOKEN_ERROR("303", "token错误"),
    TEMPERATURE_BOUND_DEPARTMENT_ERROR("310", "不能修改默认规则"),

    NON_RECORD("320", "未找到该条记录"),
    ERROR_RECORD("321", "错误记录"),

    UPDATE_ERROR("330", "更新失败"),

    UNKNOWN_ERROR("500", "系统繁忙，请稍后再试....");

    private String code;

    private String desc;

    ErrorCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }


    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "[" + this.code + "]" + this.desc;
    }
}
