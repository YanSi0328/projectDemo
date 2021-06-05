package com.finance.entity;

import lombok.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author:Wang
 * @className: HistoryFinancing
 * @description:
 * @date: 2021/6/1 16:14
 * @version:0.1
 * @since:1.8
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HistoryFinancing {
    private Integer hid; //融资id记录
    private String enterpriseName;//企业名称
    private Date investDate; //投资日期
    private String investRound;//投资轮
    private Double investAmount;//投资金额
    private Double investValuation;//投后估值
    private Integer stockIssue;//总发行股数
    private Double stockPrice;//每股价格

    public HistoryFinancing(ResultSet rs) {
        try {
            this.enterpriseName = rs.getString("enterprise_name");
            this.investDate = rs.getDate("invest_date");
            this.investRound = rs.getString("invest_round");
            this.investAmount = rs.getDouble("invest_amount");
            this.investValuation = rs.getDouble("invest_ valuation");
            this.stockIssue = rs.getInt("stock_issue");
            this.stockPrice = rs.getDouble("stock_price");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
