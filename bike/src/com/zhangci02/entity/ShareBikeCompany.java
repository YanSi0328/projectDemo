package com.zhangci02.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ClassName: ShareBikeCompany
 * <p>
 * Author: ZhangCi
 * Description: ʵ���ࣺ������˾
 * Date: 2021/4/1 8:28
 * Version: 0.1
 * Since: JDK 1.8
 */
@Setter
@Getter
@AllArgsConstructor
public class ShareBikeCompany {

    private Integer bikeCompanyId;//������˾���
    private String bikeCompanyName;//������˾����
    private ShareBike[] shareBikes;//������˾ӵ�еĵ���
    private Integer total;//��˾�ĵ�������
    private Integer lendTimes;//��˾����ĵ�������
}
