package com.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * ClassName: ProdBasic
 * Author: ZhangCi
 *
 * @description: 产品基础信息
 * @date: 2021/5/27 22:53
 * @version: 0.1
 * @since: 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdBasic {
    // 基本信息
    private Integer prodId;                     // 产品id
    private String prodName;                    // 产品名称
    private String prodCategory;                // 产品系列
    private String prodSecCategory;            // 产品类别：二级类别，产品审核中的业务类型
    private String adminOrgan;                  // 管理机构
    private Double annualizedYield;            // 年化收益率
    private String fundCurrency;                // 货币类型

    // 认购属性
    private String openDate;                   // 开放日期
    private String subscriptCycle;             // 认购周期
    private Double relativeManageCost;        // 基金管理费率
    private Double subscriptionRate;          // 认购费率
    private Double startAmount;                // 起投金额
    private String chargeMode;                 // 认购费收取方式
    // 赎回属性
    private String redeemCycle;                // 赎回周期
    private Double initRedeemAmount;           // 赎回起始金额
    private Double redeemRate;                 // 赎回费率
    private Integer redeemLockUp;              // 锁定期
    private String reviewer;                   // 审批人

    // 其他信息
    private String creator;                    // 产品创建者(当前系统的登录用户)
    private String createTime;           // 创建时间 数据库层面进行解决
    private String updateTime;           // 产品更新时间 数据库层面进行解决
    private Integer reviewStatus;               // 产品审核状态 默认值 1
    private LocalDateTime reviewTime;           // 产品审核时间 数据库层面进行解决


    //  查询产品名称：提供给产品推荐中的新增推荐功能
    public ProdBasic(String prodName) {
        this.prodName = prodName;
    }

    // 查询产品审核状态、审核人；提供给产品审核管理中的审核功能
    public ProdBasic(String prodName, Integer reviewStatus) {
        this.prodName = prodName;
        this.reviewStatus = reviewStatus;
    }

    // 查询：审核信息
    public ProdBasic(String prodName, String prodSecCategory, String creator, String createTime, String updateTime) {
        this.prodName = prodName;
        this.prodSecCategory = prodSecCategory;
        this.creator = creator;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // 查询：产品名、产品系列、系列名、管理机构、币种、开放日期、审核状态
    public ProdBasic(Integer prodId, String prodName, String prodCategory, String prodSecCategory, String adminOrgan,
                     String fundCurrency, String openDate, Integer reviewStatus) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodCategory = prodCategory;
        this.prodSecCategory = prodSecCategory;
        this.adminOrgan = adminOrgan;
        this.fundCurrency = fundCurrency;
        this.openDate = openDate;
        this.reviewStatus = reviewStatus;
    }

    //    新增：
    //    修改：
    // 公共字段 ： 17个
    // prodCategory, prodSecCategory, prodName, adminOrgan, annualizedYield,
    //                fundCurrency, opDate, subscriptCycle, relativeManageCost, subscriptionRate, startAmount,
    //                chargeMode, redeemCycle, initRedeemAmount, redeemRate, redeemLockUp, reviewer
    public ProdBasic(String prodCategory, String prodSecCategory, String prodName, String adminOrgan, Double annualizedYield,
                     String fundCurrency, String openDate, String subscriptCycle, Double relativeManageCost, Double subscriptionRate,
                     Double startAmount, String chargeMode, String redeemCycle, Double initRedeemAmount, Double redeemRate,
                     Integer redeemLockUp, String reviewer) {
        this.prodName = prodName;
        this.prodCategory = prodCategory;
        this.prodSecCategory = prodSecCategory;
        this.adminOrgan = adminOrgan;
        this.annualizedYield = annualizedYield;
        this.fundCurrency = fundCurrency;
        this.openDate = openDate;
        this.subscriptCycle = subscriptCycle;
        this.relativeManageCost = relativeManageCost;
        this.subscriptionRate = subscriptionRate;
        this.startAmount = startAmount;
        this.chargeMode = chargeMode;
        this.redeemCycle = redeemCycle;
        this.initRedeemAmount = initRedeemAmount;
        this.redeemRate = redeemRate;
        this.redeemLockUp = redeemLockUp;
        this.reviewer = reviewer;
    }
}
