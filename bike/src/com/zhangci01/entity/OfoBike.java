package com.zhangci01.entity;

/**
 * ClassName: OfoBike
 * <p>
 * Author: ZhangCi
 * Description: 派生类：ofo单车
 * Date: 2021/3/25 22:15
 * Version: 0.1
 * Since: JDK 1.8
 */
public class OfoBike extends ShareBike {

    public OfoBike(int bikeId, String bikeName, int bikeStatus, String borrowTime) {
        super(bikeId, bikeName, bikeStatus, borrowTime);
    }
}
