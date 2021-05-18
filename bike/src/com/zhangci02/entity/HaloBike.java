package com.zhangci02.entity;

/**
 * ClassName: HaloBike
 * <p>
 * Author: ZhangCi
 * Description: 实体类：哈罗单车
 * Date: 2021/4/1 8:29
 * Version: 0.1
 * Since: JDK 1.8
 */
public class HaloBike extends ShareBike {
    public HaloBike(Integer bikeId, String bikeName, Integer bikeStatus, String borrowTime) {
        super(bikeId, bikeName, bikeStatus, borrowTime);
    }
}
