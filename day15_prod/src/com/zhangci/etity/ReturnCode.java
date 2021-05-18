package com.zhangci.etity;

/**
 * ClassName: ReturnCode
 * Author: ZhangCi
 *
 * @description: 状态码信息
 * @date: 2021/5/14 21:33
 * @version: 0.1
 * @since: 1.8
 */
public enum ReturnCode {
    STATUS_SUCCESS(2000,"请求成功"),
    STATUS_ERROR(4004,"没有查到相关信息");

    private Integer code;
    private String msg;

    ReturnCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
