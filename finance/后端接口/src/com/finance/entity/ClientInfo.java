package com.finance.entity;

import lombok.*;

import java.sql.ResultSet;

/**
 * @author:Wang
 * @className: ClientInfo
 * @description: 客户信息类
 * @date: 2021/5/29 22:33
 * @version:0.1
 * @since:1.8
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientInfo {
    private Integer id; //客户信息记录id
    private String clientName;//客户名称
    private String clientPhone;//客户手机号
    private Integer clientId;//客户编号
    private Integer enterpriseId;//独角兽企业编号
    private Integer stockAmount;//股票数量
    private Double enterpriseItprice;//原始股价格
    private String enterpriseName;//独角兽企业名字

    public ClientInfo(String clientName, String clientPhone, Integer stockAmount, Double enterpriseItprice) {
        this.clientName = clientName;
        this.clientPhone = clientPhone;

        this.stockAmount = stockAmount;
        this.enterpriseItprice = enterpriseItprice;
    }

    public ClientInfo(String clientName, String clientPhone) {
        this.clientName = clientName;
        this.clientPhone = clientPhone;
    }

    @SneakyThrows
    public ClientInfo(ResultSet rs) {
        this.clientName = rs.getString("client_name");
        this.clientPhone = rs.getString("client_phone");
        this.enterpriseName = rs.getString("enterprise_name");
        this.stockAmount = rs.getInt("stock_amount");
        this.enterpriseItprice = rs.getDouble("enterprise_itprice");
        this.clientId = rs.getInt("client_id");
    }
}
