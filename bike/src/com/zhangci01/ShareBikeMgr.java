package com.zhangci01;

import com.zhangci01.consts.BikeConstants;
import com.zhangci01.dao.BikeDao;
import com.zhangci01.dao.impl.BikeDaoImpl;
import com.zhangci01.entity.*;

import java.util.Arrays;
import java.util.Scanner;

/**
 * ClassName: ShareBikeMgr
 * <p>
 * Author: ZhangCi
 * Description: 管理单车类
 * Date: 2021/3/25 22:26
 * Version: 0.1
 * Since: JDK 1.8
 */
public class ShareBikeMgr {

    private ShareBikeCompany[] shareBikeCompanies;
    private Scanner input;
    private BikeDao bikeDao;

    //初始化
    private void init() {

        input = new Scanner(System.in);
        bikeDao = new BikeDaoImpl();


        //存放单车
        ShareBike[] shareBikeOfo = new ShareBike[5];
        ShareBike[] shareBikeMo = new ShareBike[5];
        ShareBike[] shareBikeHalo = new ShareBike[5];
        for (int i = 1; i <= 5; i++) {
            shareBikeOfo[i - 1] = new OfoBike(i, BikeConstants.OFO_BIKE_NAME + i, 0, "2021/03/25 16:00:00");
            shareBikeMo[i - 1] = new MoBike(i, BikeConstants.MO_BIKE_NAME + i, 1, null);
            shareBikeHalo[i - 1] = new HaloBike(i, BikeConstants.HALO_BIKE_NAME + i, 1, null);
        }

        //三个单车公司
        shareBikeCompanies = new ShareBikeCompany[3];
        shareBikeCompanies[0] = new ShareBikeCompany(1, BikeConstants.OFO_COMPANY_NAME, shareBikeOfo, 5, 10);
        shareBikeCompanies[1] = new ShareBikeCompany(2, BikeConstants.HALO_COMPANY_NAME, shareBikeHalo, 5, 20);
        shareBikeCompanies[2] = new ShareBikeCompany(3, BikeConstants.MO_COMPANY_NAME, shareBikeMo, 5, 30);

    }

    //启动
    public void startMenu() {

        System.out.println("**********迷你单车管理系统**********");
        System.out.println("-----------------------------------");
        System.out.println("**        1.投  放 Bike          **");
        System.out.println("**        2.查  看 Bike          **");
        System.out.println("**        3.删  除 Bike          **");
        System.out.println("**        4.借  出 Bike          **");
        System.out.println("**        5.归  还 Bike          **");
        System.out.println("**        6.Bike 排行榜          **");
        System.out.println("**        7.退 出 系 统          **");
        System.out.println("----------------------------------");

        System.out.print("请输入对应的菜单编号: ");
        int getChoice = input.nextInt();
        switch (getChoice) {
            case 1:
                addBike();
                break;
            case 2:
                bikeDao.selectBike(shareBikeCompanies);
                break;
            case 3:
                deleteBike();
                break;
            case 4:
                borrowBike();
                break;
            case 5:
                returnBike();
                break;
            case 6:
                sortBike();
                break;
            case 7:
                System.out.println("谢谢使用，系统已经退出！");
                return;
            default:
                System.out.println("输入非法");
        }
        returnMenu();
    }

    //排行榜
    private void sortBike() {
        System.out.println(BikeConstants.MAIN_MENU_NAVIGATION + BikeConstants.MAIN_MENU_LISTBIKE);
        System.out.println();

        //重新复制一个数组 用于存放排序的公司
        ShareBikeCompany[] companies = Arrays.copyOf(this.shareBikeCompanies, shareBikeCompanies.length);

        //排序规则：通过借出次数进行排序
        int lenCompany = shareBikeCompanies.length;
        for (int i = 1; i < lenCompany; i++) {
            for (int j = 0; j < lenCompany - i; j++) {
                if (companies[j].getCount() < companies[j + 1].getCount()) {
                    //交换元素
                    ShareBikeCompany temp = companies[j];
                    companies[j] = companies[j + 1];
                    companies[j + 1] = temp;
                }

            }
        }
        System.out.println("公司名称    借车次数");
        for (ShareBikeCompany company : companies) {
            System.out.println(company.getBikeCompanyName() + "     " + company.getCount());
        }
        System.out.println("--------------------------");
    }

    //归还单车
    private void returnBike() {
        System.out.println(BikeConstants.MAIN_MENU_NAVIGATION + BikeConstants.MAIN_MENU_RETBIKE);
        System.out.println();
        //选择要操作的单车品牌
        int getChoose = chooseCompany();
        //把getChoose当做索引传入单车公司，为什么要减一？
        //因为单车公司放入了一个长度为三的数组，而数组的索引为从0开始，0、1、2分别代表一个单车公司，然而菜单的标识显示是1-3，响应的减少了1.
        bikeDao.returnBike(shareBikeCompanies[getChoose - 1], input);

    }

    //借出单车
    private void borrowBike() {
        System.out.println(BikeConstants.MAIN_MENU_NAVIGATION + BikeConstants.MAIN_MENU_BORBIKE);
        System.out.println();
        //选择要操作的单车品牌
        int getChoose = chooseCompany();
        bikeDao.borrowBike(shareBikeCompanies[getChoose - 1], input);
    }

    //删除单车
    private void deleteBike() {
        System.out.println(BikeConstants.MAIN_MENU_NAVIGATION + BikeConstants.MAIN_MENU_DELBIKE);
        System.out.println();
        //选择要操作的单车品牌
        int getChoose = chooseCompany();
        bikeDao.deleteBike(shareBikeCompanies[getChoose - 1], input);
    }

    //投放单车
    private void addBike() {
        System.out.println(BikeConstants.MAIN_MENU_NAVIGATION + BikeConstants.MAIN_MENU_ADDBIKE);
        System.out.println();
        //选择要操作的公司
        int getChoose = chooseCompany();
        System.out.print("请输入要投放的数量");
        int addNum = input.nextInt();
        //将数据返回业务逻辑的处理
        bikeDao.addBike(addNum, shareBikeCompanies[getChoose - 1]);
    }

    //投放单车品牌的选择
    private int chooseCompany() {
        System.out.println("      1." + BikeConstants.OFO_BIKE_NAME);
        System.out.println("      2." + BikeConstants.HALO_BIKE_NAME);
        System.out.println("      3." + BikeConstants.MO_BIKE_NAME);
        System.out.print("请输入要操作的单车品牌: ");
        //后期记得优化一下
        return input.nextInt();
    }


    //循环菜单
    private void returnMenu() {
        System.out.print("输入0返回。。。。");
        int num = input.nextInt();
        if (num == 0) {
            startMenu();
        }
        System.out.println("输入非法，系统退出");
    }


    public ShareBikeMgr() {
        init();
    }
}
