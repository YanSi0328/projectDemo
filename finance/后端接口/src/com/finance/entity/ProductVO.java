package com.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * ClassName: ProdBasic
 * Author: ZhangCi
 *
 * @description: 商品实体类
 * @date: 2021/5/21 17:39
 * @version: 0.1
 * @since: 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {
    private Integer prodId;                     // 产品编号
    private String prodCategory;                // 产品系列： 3月内/半年内/一年内
    private String prodSecCategory;             // 产品系列：二级类别
    private String prodName;                    // 产品名称
    private String prodType;                    // 产品类型：阳光私募；对冲基金；香港私募股权；其他基金；共同基金
    private String prodCategoryName;            // 产品类别名称
    private BigDecimal opDate;                  // 开放日期
    private String fundCurrency;                // 币种
    private BigDecimal subscriptionRate;        // 认购费率
    private BigDecimal relativeManageCost;      // 基金管理费率
    private BigDecimal startAmount;             // 起投金额
    private String chargeMode;                  // 认购费收取方式
    private String redeemCycle;               // 赎回周期
    private BigDecimal redeemRate;            // 赎回费率
    private String redeemLockUp;              // 锁定期
    private BigDecimal growthRate;              // 累计增长率
    private Integer prodLaunchStatus;           // 上线状态

    // 系列下产品列表接口
    public ProductVO(Integer prodId, String prodName, String prodCategory, String prodCategoryName, String prodSecCategory,
                     BigDecimal opDate, String fundCurrency, Integer prodLaunchStatus) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodCategory = prodCategory;
        this.prodCategoryName = prodCategoryName;
        this.prodSecCategory = prodSecCategory;
        this.opDate = opDate;
        this.fundCurrency = fundCurrency;
        this.prodLaunchStatus = prodLaunchStatus;
    }


    //  推荐基金产品接口
    public ProductVO(Integer prodId, String prodName, String prodCategory, String prodCategoryName, String prodSecCategory,
                     BigDecimal opDate, String fundCurrency, BigDecimal growthRate) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodCategory = prodCategory;
        this.prodCategoryName = prodCategoryName;
        this.prodSecCategory = prodSecCategory;
        this.opDate = opDate;
        this.fundCurrency = fundCurrency;
        this.growthRate = growthRate;
    }

    // 可申购基金产品接口
    public ProductVO(Integer prodId, String prodName, String prodType, String prodCategory, String prodCategoryName,
                     String prodSecCategory, BigDecimal opDate, String fundCurrency, BigDecimal subscriptionRate,
                     BigDecimal relativeManageCost, BigDecimal startAmount, String chargeMode, String redemingCycle,
                     BigDecimal redemingRate, String redemingLockUp) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodType = prodType;
        this.prodCategory = prodCategory;
        this.prodCategoryName = prodCategoryName;
        this.prodSecCategory = prodSecCategory;
        this.opDate = opDate;
        this.fundCurrency = fundCurrency;
        this.subscriptionRate = subscriptionRate;
        this.relativeManageCost = relativeManageCost;
        this.startAmount = startAmount;
        this.chargeMode = chargeMode;
        this.redeemCycle = redemingCycle;
        this.redeemRate = redemingRate;
        this.redeemLockUp = redemingLockUp;
    }

}
