package com.zhangci.entity;

import lombok.*;

import java.io.Serializable;

/**
 * ClassName: OrderDetails
 * <p>
 * Author: ZhangCi
 * Description: 订单详情表
 * Date: 2021/4/12 21:44
 * Version: 0.1
 * Since: JDK 1.8
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = -380720619861176631L;
    private int oDetailsId;         //Id 订单详情id 自增 主键
    @NonNull
    private Integer oDetailsOrderId;    //订单id int （与订单信息表订单ID有关系） 非空


    //改为int后会出现覆盖的情况
    @NonNull
    private Integer oDetailsGoodsId;    //购买商品id int （与商品信息表的商品编号有关系）非空
    @NonNull
    private Integer oDetailsBGNum;      //购买商品数量 int 非空

    public OrderDetails(@NonNull Integer oDetailsOrderId, @NonNull Integer oDetailsGoodsId, @NonNull Integer oDetailsBGNum) {
        this.oDetailsOrderId = oDetailsOrderId;
        this.oDetailsGoodsId = oDetailsGoodsId;
        this.oDetailsBGNum = oDetailsBGNum;
    }
}
