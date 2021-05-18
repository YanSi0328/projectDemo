package com.zhangci02.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ClassName: ShareBikeCompany
 * <p>
 * Author: ZhangCi
 * Description: 实体类：单车公司
 * Date: 2021/4/1 8:28
 * Version: 0.1
 * Since: JDK 1.8
 */
@Setter
@Getter
@AllArgsConstructor
public class ShareBikeCompany {

    private Integer bikeCompanyId;//单车公司编号
    private String bikeCompanyName;//单车公司名称
    private ShareBike[] shareBikes;//单车公司拥有的单车
    private Integer total;//公司的单车数量
    private Integer lendTimes;//公司借出的单车数量
}
