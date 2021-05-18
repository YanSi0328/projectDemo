package com.zhangci.etity;

import lombok.*;

import java.math.BigDecimal;

/**
 * ClassName: Product
 * Author: ZhangCi
 *
 * @description: 商品信息
 * @date: 2021/5/14 10:06
 * @version: 0.1
 * @since: 1.8
 */
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    // pid、pname、price、pimg、pdesc
    private String pid;
    private String pname;
    private BigDecimal price;
    private String pimg;
    private String pdesc;

    public Product(String proid, String prodname) {
        this.pid = proid;
        this.pname = prodname;
    }
}
