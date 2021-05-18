package com.zhangci02.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * ClassName: ShareBike
 * <p>
 * Author: ZhangCi
 * Description: 基类：单车类
 * Date: 2021/4/1 8:29
 * Version: 0.1
 * Since: JDK 1.8
 */
@Getter
@Setter
@AllArgsConstructor
public class ShareBike {

    private Integer bikeId;//单车编号
    private String bikeName;//单车名称
    private Integer bikeStatus;//单车状态：0可借，1已借出
    private String borrowTime;//单车借出时间

}
