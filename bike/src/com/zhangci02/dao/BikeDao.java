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
     * �鿴������Ϣ
     *
     * @param bikeCompanies ��˾�����ŵĵ�����Ϣ
     */
    void selectBike(ShareBikeCompany[] bikeCompanies);

    /**
     * Ͷ�ŵ���
     *
     * @param addNum      Ͷ�ŵĵ�������
     * @param bikeCompany ������˾
     */
    void addBike(int addNum, ShareBikeCompany bikeCompany);

    /**
     * ɾ��������Ϣ
     *
     * @param bikeCompany ������˾
     * @param input       �������ĵ������
     */
    void deleteBike(ShareBikeCompany bikeCompany, Scanner input);

    /**
     * �������
     *
     * @param bikeCompany ������˾
     * @param input       �������ĵ������
     */
    void borrowBike(ShareBikeCompany bikeCompany, Scanner input);

    /**
     * �黹����
     *
     * @param bikeCompany Ҫ�黹�ĵ�����˾
     * @param input       ��ȡ����ĵ������
     */
    void returnBike(ShareBikeCompany bikeCompany, Scanner input);

    /**
     * ��������
     * @param bikeCompanies ��˾
     */
    void sortBike(ShareBikeCompany[] bikeCompanies);
}
