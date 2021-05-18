package com.zhangci02.entity;

/**
 * ClassName: OfoBike
 * <p>
 * Author: ZhangCi
 * Description: 实体类：Ofo单车
 * Date: 2021/4/1 8:29
 * Version: 0.1
 * Since: JDK 1.8
 */
public class OfoBike extends ShareBike{
    public OfoBike(Integer bikeId, String bikeName, Integer bikeStatus, String borrowTime) {
        super(bikeId, bikeName, bikeStatus, borrowTime);
    }
}
