package com.finance.cons;

/**
 * ClassName: ReturnCode
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/20 21:54
 * @version: 0.1
 * @since: 1.8
 */
public enum  ReturnCode {
    NO_AUTHORITY(100,"未授权"),
    NO_LOGIN(200,"未登录"),
    LOGIN_FAILED(201,"登录失败"),
    LOGIN_SUCCESS(202,"登录成功"),
    STATUS_SUCCESS(300,"操作成功"),
    STATUS_ERROR(400,"操作失败");

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
