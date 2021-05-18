package com.zhangci02.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * ClassName: ShareBike
 * <p>
 * Author: ZhangCi
 * Description: ���ࣺ������
 * Date: 2021/4/1 8:29
 * Version: 0.1
 * Since: JDK 1.8
 */
@Getter
@Setter
@AllArgsConstructor
public class ShareBike {

    private Integer bikeId;//�������
    private String bikeName;//��������
    private Integer bikeStatus;//����״̬��0�ɽ裬1�ѽ��
    private String borrowTime;//�������ʱ��

}
