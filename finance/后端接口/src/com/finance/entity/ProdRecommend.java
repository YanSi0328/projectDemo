package com.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ClassName: ProdRecom
 * Author: ZhangCi
 *
 * @description: 实体类-推荐
 * @date: 2021/5/31 21:49
 * @version: 0.1
 * @since: 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdRecommend {
    private Integer id;                       // 净值id 在数据库层面进行解决
    private String prodName;                 // 产品名称
    private String prodCategory;            // 产品系列
    private Integer degree;                  // 推荐度：1 热门推荐；2 普通推荐；3 下架
    private Integer firstRound;              // 是否首发：1是；2否
    private Integer onlinePurchase;          // 是否可线上申购：1可；2不可
    private Integer rank;                     // 推荐排序，手动实现自增
    private Integer investVisible;           // 是否投顾端可见：1可见；2不可见
    private String reason;                    // 推荐理由
    private LocalDateTime createTime;        // 创建时间 在数据库层面进行解决
    private LocalDateTime updateTime;        //  更新时间 在数据库层面进行解决

    // 查询
    public ProdRecommend(Integer id, String prodName, String pCategory, Integer degree, Integer firstRound, Integer onlinePurchase, Integer rank) {
        this.id = id;
        this.prodName = prodName;
        this.prodCategory = pCategory;
        this.degree = degree;
        this.firstRound = firstRound;
        this.onlinePurchase = onlinePurchase;
        this.rank = rank;
    }

    // 新增、修改

    public ProdRecommend(String prodName, Integer degree, Integer investVisible, Integer firstRound, Integer onlinePurchase, String reason) {
        this.prodName = prodName;
        this.degree = degree;
        this.investVisible = investVisible;
        this.firstRound = firstRound;
        this.onlinePurchase = onlinePurchase;
        this.reason = reason;
    }

}
