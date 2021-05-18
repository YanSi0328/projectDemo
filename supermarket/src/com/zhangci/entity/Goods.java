package com.zhangci.entity;

import lombok.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * ClassName: Goods
 * <p>
 * Author: ZhangCi
 * Description: 商品信息表
 * Date: 2021/4/12 21:26
 * Version: 0.1
 * Since: JDK 1.8
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {

    //唯一序列号
    private static final long serialVersionUID = 7325270731352836289L;
    private int goodsId;                //商品编号 id	int 自增 唯一主键
    @NonNull
    private String goodsName;           //商品名	varchar（20） 非空
    @NonNull
    private String goodsType;           //商品所属类型   非空
    @NonNull
    private int goodsNum;               //商品数量	int 非空 默认值0
    @NonNull
    private float goodsPrice;           //单价 float 非空
    private int goodsStatus;            //商品状态 tinyint(4) 1-正常，2-下架，3-删除
    private float goodsDiscount;        //折扣(默认是10) float
    private LocalDateTime goodsCreateTime;     //创建时间 datetime 可空
    private LocalDateTime goodsUpdateTime;     //修改时间 datetime 可空

    //由于数据库中id可以实现自增，故可以不传值
    public Goods(@NonNull String goodsName, @NonNull String goodsType, @NonNull int goodsNum, @NonNull float goodsPrice, int goodsStatus, float goodsDiscount, LocalDateTime goodsCreateTime, LocalDateTime goodsUpdateTime) {
        this.goodsName = goodsName;
        this.goodsType = goodsType;
        this.goodsNum = goodsNum;
        this.goodsPrice = goodsPrice;
        this.goodsStatus = goodsStatus;
        this.goodsDiscount = goodsDiscount;
        this.goodsCreateTime = goodsCreateTime;
        this.goodsUpdateTime = goodsUpdateTime;
    }

    //结果集的处理
    public Goods(ResultSet rs) throws SQLException {
        this(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getInt("num"),
                rs.getFloat("price"),
                rs.getInt("status"),
                rs.getFloat("discount"),
                (LocalDateTime) rs.getObject("create_time"),
                (LocalDateTime) rs.getObject("update_time")
        );
    }
}
