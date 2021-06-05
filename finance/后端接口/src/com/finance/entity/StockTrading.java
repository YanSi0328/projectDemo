package com.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author:Wang
 * @className: StockTrading
 * @description:挂单信息类
 * @date: 2021/5/31 21:17
 * @version:0.1
 * @since:1.8
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockTrading {
    private Integer enterpriseId;//企业id

    private double fBuyPrice;//买一
    private double sBuyPrice;//买二
    private double tBuyPrice;//买三

    private double fSellPrice;//卖一
    private double sSellPrice;//卖二
    private double tSellPrice;//卖三

    //根据企业id创建挂单信息类
    public StockTrading(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}
