package com.cp.framwork.constants;

import com.cp.framwork.core.ApiCodeMessageItem;


/**
 * @Description 常量 .</br>
 * <></>
 * @Date 2020/9/5 18:01
 * @Version 1.0.0
 **/
public class ApiCodeConstants {
    public static final ApiCodeMessageItem DEFAULT_SUCCESS = new ApiCodeMessageItem("0", "成功");
    public static ApiCodeMessageItem DEFAULT_ERROR = new ApiCodeMessageItem("99999999", "网络异常");

    /**
     * 通用操作0开头
     */
    public static final ApiCodeMessageItem REQUEST_TIMEOUT = new ApiCodeMessageItem("000998", "系统繁忙，请稍后再试", "超时或者通信异常");

    /***
     * 1 开头 订单问题
     */
    public static ApiCodeMessageItem NOT_EXISTS_ORDERS = new ApiCodeMessageItem("110000", "订单信息不存在");
    public static ApiCodeMessageItem NOT_EXISTS_RECEIPT = new ApiCodeMessageItem("110003", "票据信息不存在");
    public static ApiCodeMessageItem RECEIPT_CHECK_FAILURE = new ApiCodeMessageItem("110002", "票据校验失败");



}
