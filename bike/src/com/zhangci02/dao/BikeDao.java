package com.zhangci02.dao;

import com.zhangci02.entity.ShareBikeCompany;

import java.util.Scanner;

/**
 * ClassName: BikeDao
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/1 8:12
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface BikeDao {

    /**
     * 查看单车信息
     *
     * @param bikeCompanies 公司里面存放的单车信息
     */
    void selectBike(ShareBikeCompany[] bikeCompanies);

    /**
     * 投放单车
     *
     * @param addNum      投放的单车数量
     * @param bikeCompany 单车公司
     */
    void addBike(int addNum, ShareBikeCompany bikeCompany);

    /**
     * 删除单车信息
     *
     * @param bikeCompany 单车公司
     * @param input       获得输入的单车编号
     */
    void deleteBike(ShareBikeCompany bikeCompany, Scanner input);

    /**
     * 借出单车
     *
     * @param bikeCompany 单车公司
     * @param input       获得输入的单车编号
     */
    void borrowBike(ShareBikeCompany bikeCompany, Scanner input);

    /**
     * 归还单车
     *
     * @param bikeCompany 要归还的单车公司
     * @param input       获取输入的单车编号
     */
    void returnBike(ShareBikeCompany bikeCompany, Scanner input);

    /**
     * 单车排序
     * @param bikeCompanies 公司
     */
    void sortBike(ShareBikeCompany[] bikeCompanies);
}
