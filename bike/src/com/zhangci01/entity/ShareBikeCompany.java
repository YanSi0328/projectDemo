package com.zhangci01.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ClassName: ShareBikeCompany
 * <p>
 * Author: ZhangCi
 * Description: 实体类：单车公司
 * Date: 2021/3/25 22:02
 * Version: 0.1
 * Since: JDK 1.8
 */
@Setter
@Getter
@AllArgsConstructor
public class ShareBikeCompany {
    /*
    公司id
    公司名称
    公司持有单车
    公司单车总量
    公司单车借出次数
     */
    private int bikeCompanyId;
    private String bikeCompanyName;
    private ShareBike[] sharedBikes;
    private int sum;
    private int count;



}
