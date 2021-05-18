package com.zhangci.entity;

import lombok.*;

import java.io.Serializable;

/**
 * ClassName: OrderMsg
 * <p>
 * Author: ZhangCi
 * Description: 订单信息表
 * Date: 2021/4/12 21:40
 * Version: 0.1
 * Since: JDK 1.8
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderMsg implements Serializable {

    private static final long serialVersionUID = 4390812940097500618L;
    private int orderId;        //订单ID int 自增  唯一
    @NonNull
    private int orderUserId;    //用户ID int 非空
    @NonNull
    private double orderTotal;  //订单总金额	decimal非空
    @NonNull
    private String orderTime;   //下单时间 datetime 非空
    @NonNull
    private int orderType;      //支付类型 tinyint(2) （0-现金or  1-余额）非空

    public OrderMsg(@NonNull int orderUserId, @NonNull double orderTotal, @NonNull int orderType) {
        this.orderUserId = orderUserId;
        this.orderTotal = orderTotal;
        this.orderType = orderType;
    }
}
