package com.zhangci.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * ClassName: ServiceResponse
 * <p>
 * Author: ZhangCi
 * Description: 服务端返回的信息
 * Date: 2021/4/19 15:40
 * Version: 0.1
 * Since: JDK 1.8
 */
@Setter
@Getter
@ToString
public class ServiceResponse<T> implements Serializable {
    private static final long serialVersionUID = 2417365917632620555L;

    private Integer code;
    private String msg;
    private T data;

    private ServiceResponse() {
    }

    // 私有化构造
    private ServiceResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ServiceResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ServiceResponse(T data) {
        this.data = data;
    }

    // 返回所有的信息
    public static <T> ServiceResponse<T> success(T data) {
        return new ServiceResponse<>(EnumCode.SUCCESS.getCode(), EnumCode.SUCCESS.getMsg(), data);
    }

    public static <T> ServiceResponse<T> success() {
        return new ServiceResponse<>(EnumCode.SUCCESS.getCode(), EnumCode.SUCCESS.getMsg());
    }

    // 仅返回错误码和信息
    public static <T> ServiceResponse<T> error() {
        return new ServiceResponse<>(EnumCode.ERROR.getCode(), EnumCode.ERROR.getMsg());
    }

    public static <T> ServiceResponse<T> error(T data) {
        return new ServiceResponse<>(EnumCode.ERROR.getCode(), EnumCode.ERROR.getMsg(), data);
    }
}
