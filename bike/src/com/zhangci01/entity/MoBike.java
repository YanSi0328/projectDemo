package com.zhangci01.entity;

/**
 * ClassName: MoBike
 * <p>
 * Author: ZhangCi
 * Description: 派生类：摩拜单车
 * Date: 2021/3/25 22:18
 * Version: 0.1
 * Since: JDK 1.8
 */
public class MoBike extends ShareBike {
    public MoBike(int bikeId, String bikeName, int bikeStatus, String borrowTime) {
        super(bikeId, bikeName, bikeStatus, borrowTime);
    }
}
