package com.zhangci02.entity;

/**
 * ClassName: MoBaiBike
 * <p>
 * Author: ZhangCi
 * Description: ʵ���ࣺĦ�ݵ���
 * Date: 2021/4/1 8:29
 * Version: 0.1
 * Since: JDK 1.8
 */
public class MoBaiBike extends ShareBike{
    public MoBaiBike(Integer bikeId, String bikeName, Integer bikeStatus, String borrowTime) {
        super(bikeId, bikeName, bikeStatus, borrowTime);
    }
}
