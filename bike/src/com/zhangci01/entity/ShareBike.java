package com.zhangci01.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ClassName: ShareBike
 * <p>
 * Author: ZhangCi
 * Description: 、基类：单车类
 * Date: 2021/3/25 21:52
 * Version: 0.1
 * Since: JDK 1.8
 */

@Setter
@Getter
@AllArgsConstructor
public class ShareBike {
    /*
    单车编号
    单车名称
    是否借出
    借出时间
     */
    private int bikeId;
    private String bikeName;
    private int bikeStatus;
    private String borrowTime;


}
