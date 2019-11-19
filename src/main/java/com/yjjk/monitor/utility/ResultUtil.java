/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: ResultUtil
 * Author:   CentreS
 * Date:     2019/8/20 14:50
 * Description: 公共相应结果成功失败的静态方法调用
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.utility;

/**
 * @Description: 公共相应结果成功失败的静态方法调用
 * @author CentreS
 * @create 2019/8/20
 */

import com.yjjk.monitor.configer.CommonResult;
import com.yjjk.monitor.constant.ErrorCodeEnum;

/**
 * 公共响应结果成功失败的静态方法调用
 *
 */
public class ResultUtil {


    /**
     * return success
     *
     * @param data
     * @return
     */
    public static <T> CommonResult<T> returnSuccess(T data) {
        CommonResult<T> result = new CommonResult();
        result.setCode(ErrorCodeEnum.SUCCESS.getCode());
        result.setSuccess(true);
        result.setData(data);
        result.setMessage(ErrorCodeEnum.SUCCESS.getDesc());
        return result;
    }
    /**
     * return success
     *
     * @param data
     * @return
     */
    public static <T> CommonResult<T> returnSuccess(T data,String message) {
        CommonResult<T> result = new CommonResult();
        result.setCode(ErrorCodeEnum.SUCCESS.getCode());
        result.setSuccess(true);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    /**
     * return error
     *
     * @param code error code
     * @param msg  error message
     * @return
     */
    public static CommonResult returnError(String code, String msg) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setData("");
        result.setMessage(msg);
        return result;

    }

    /**
     * use enum
     *
     * @param status
     * @return
     */
    public static CommonResult returnError(ErrorCodeEnum status) {
        return returnError(status.getCode(), status.getDesc());
    }
}
