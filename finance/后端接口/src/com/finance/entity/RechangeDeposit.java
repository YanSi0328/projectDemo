package com.finance.entity;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


/**
 * @author:Wang
 * @className: RechangeDeposit
 * @description:
 * @date: 2021/6/3 13:35
 * @version:0.1
 * @since:1.8
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RechangeDeposit {
    private Integer clientId; //客户编号
    private String clientName;//客户名称
    private String clientPhone;//客户联系方式
    private Integer tradType;//交易类型
    private Double money;//交易金额
    private LocalDateTime startTime;//请求时间
    private LocalDateTime completionTime;//处理完成时间
    private String transactionNumber;//银行交易编号
    private Integer statue;//交易状态

    public RechangeDeposit(ResultSet rs) {
        try {
            this.clientName = rs.getString("client_name");
            this.clientPhone = rs.getString("client_phone");
            this.tradType = rs.getInt("trad_type");
            this.statue = rs.getInt("statue");
            this.clientId = rs.getInt("client_id");
            this.money = rs.getDouble("money");
            this.startTime = (LocalDateTime) rs.getObject("start_time");
            this.completionTime = (LocalDateTime) rs.getObject("completion_time");
            this.transactionNumber = rs.getString("transaction_number");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public RechangeDeposit(String clientName, String clientPhone, Integer tradType, Integer statue) {
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.tradType = tradType;
        this.statue = statue;
    }
}
