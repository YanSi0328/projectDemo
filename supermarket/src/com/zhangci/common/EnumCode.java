package com.zhangci.common;


/**
 * ClassName: EnumCode
 * <p>
 * Author: ZhangCi
 * Description: code
 * Date: 2021/4/19 15:40
 * Version: 0.1
 * Since: JDK 1.8
 */
public enum EnumCode {
    SUCCESS(200, "success"),
    ERROR(404, "error");


    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    EnumCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
