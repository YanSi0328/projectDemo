package com.zhangci.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName: GoodType
 * <p>
 * Author: ZhangCi
 * Description: 商品类型表
 * Date: 2021/4/12 21:20
 * Version: 0.1
 * Since: JDK 1.8
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GoodsType implements Serializable {

    private static final long serialVersionUID = 2573519139227904906L;
    private int typeId;             //类型ID  int 自增 唯一
    private int typeParentId;       //父id   int   当父id为0时  代表的是一级类目  pid
    @NonNull
    private String typeName;        //类型名称 varchar(20) 非空
    private int typeOrParent;       //是否为父类型 tinyint(1) 1为是，0为否
    private int typeStatus;         //类型状态 tinyint(2)    1  2
    private LocalDateTime typeCreateTime;  //创建时间 datetime/timestamp 可空
    private LocalDateTime typeUpdateTime;  //修改时间 datetime/timestamp 可空

    public GoodsType(int typeParentId, @NonNull String typeName, int typeOrParent, int typeStatus) {
        this.typeParentId = typeParentId;
        this.typeName = typeName;
        this.typeOrParent = typeOrParent;
        this.typeStatus = typeStatus;
    }
}
