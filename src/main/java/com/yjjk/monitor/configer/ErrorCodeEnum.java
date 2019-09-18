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
package com.yjjk.monitor.configer;

/**
 * @Description: 错误代码枚举类
 * @author CentreS
 * @create 2019/8/20
 */
/**
 * 错误代码枚举类
 *
 */
public enum ErrorCodeEnum {

    SUCCESS("200", "success"),

    PARAM_EMPTY("301", "必选参数为空"),
    PARAM_ERROR("302", "参数格式错误"),
    ACCOUNT_ERROR("303", "用户名错误"),
    PASSWORD_ERROR("304", "密码错误"),
    ACCOUNT_EXIT_ERROR("305", "账户名已存在"),
    POST_ERROR("306", "新增失败"),
    LOGOUT_ERROR("307", "登出失败"),
    UPDATE_MANAGER_ERROR("308", "账户信息更新失败"),
    DELETE_MANAGER_ERROR("309", "删除失败"),
    TOKEN_ERROR("310", "token验证失败"),

    ORDER_ERROR("350", "订单拉起失败"),
    ORDER_PAY_ERROR("351", "支付失败"),
    Res_STATUS_ERROR("352", "预约单修改出错"),
    PHONE_ERROR("353", "手机号不能为空"),
    COUNT_ERROR("354", "  请勿连续发送"),
    CODE_ERROR("355", "  验证码发送失败"),
    COUNT_NUMBER_ERROR("356", "今日验证码次数已用完"),
    LOGIN_ERROR("357", "登录失败"),
    CODE_ISTURE_ERROR("358", "验证码错误"),
    NEW_USER_ERROR("359", "该账户为新用户"),
    LOCK_ERROR("360", "锁号失败"),
    RES_ERROR("361", "预约失败"),
    PARAM_CONT_ERROR("362", "参数错误"),
    CODE_INVALID("363", "验证码失效"),
    REFUND_ERROR("364", "申请退款失败,请稍后再试"),
    LINK_ERROR("365", "连接超时"),
    CANCEL_ERROR("366", "取消失败"),
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
