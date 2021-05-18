package com.zhangci01.dao;

import com.zhangci01.entity.ShareBikeCompany;

import java.util.Scanner;

/**
 * ClassName: BikeDao
 * <p>
 * Author: ZhangCi
 * Description: 单车类总接口
 * Date: 2021/3/26 8:27
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface BikeDao {


    /**
     * 投放单车
     * @param addNum 新增单车数量
     * @param company 新增单车品牌()
     */
    void addBike(int addNum, ShareBikeCompany company);

    /**
     * 查看单车信息
     * @param shareBikeCompanies 单车公司（单车）
     */
    void selectBike(ShareBikeCompany[] shareBikeCompanies);

    /**
     * 删除指定公司的制定单车
     * @param company 指定公司
     */
    void deleteBike(ShareBikeCompany company, Scanner input);

    //借出单车
    void borrowBike(ShareBikeCompany company, Scanner input);

    //归还单车
    void returnBike(ShareBikeCompany company, Scanner input);
}
