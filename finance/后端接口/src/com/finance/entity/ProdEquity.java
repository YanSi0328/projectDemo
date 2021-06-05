package com.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ClassName: ProdEquity
 * Author: ZhangCi
 *
 * @description: 实体类-净值
 * @date: 2021/5/31 21:19
 * @version: 0.1
 * @since: 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdEquity {
    private Integer id;                       // 净值id 在数据库层面进行解决
    private String prodName;                 // 产品名称
    private Integer average;                 // 单位净值
    private String baseDate;          // 净值基准日
    private Double growthRate;               // 累计增长率
    private LocalDateTime createTime;        // 创建时间 在数据库层面进行解决
    private LocalDateTime updateTime;        //  更新时间 在数据库层面进行解决

    // 新增、修改，注意二者的判断
    public ProdEquity(String prodName, Integer average, String baseDate, Double growthRate) {
        this.prodName = prodName;
        this.average = average;
        this.baseDate = baseDate;
        this.growthRate = growthRate;
    }
}
