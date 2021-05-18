package com.zhangci02;

import com.zhangci02.dao.impl.BikeDaoImpl;
import com.zhangci02.consts.BikeConstants;
import com.zhangci02.dao.BikeDao;
import com.zhangci02.entity.*;

import java.security.PublicKey;
import java.text.BreakIterator;
import java.util.Scanner;

/**
 * ClassName: ShareBikeMgr
 * <p>
 * Author: ZhangCi
 * Description: 单车管理
 * Date: 2021/4/1 8:15
 * Version: 0.1
 * Since: JDK 1.8
 */
public class ShareBikeMgr {
    //全局变量
    private Scanner input;
    private BikeDao bikeDao;
    private ShareBikeCompany[] bikeCompanies;


    public ShareBikeMgr() {
        initial();
    }

    //信息初始化
    private void initial() {
        input = new Scanner(System.in);
        bikeDao = new BikeDaoImpl();


        //创建单车实例，并使用数组存放单车信息
        ShareBike[] bikeOfo = new OfoBike[5];
        ShareBike[] bikeHalo = new HaloBike[5];
        ShareBike[] bikeMo = new MoBaiBike[5];

        //将信息放入数组
        for (int index = 0; index < 5; index++) {
            bikeOfo[index] = new OfoBike(BikeConstants.OFO_BIKE_ID, (BikeConstants.OFO_BIKE_NAME + BikeConstants.OFO_BIKE_ID++), 1, "2021/04/02 8:00:00");
            bikeHalo[index] = new HaloBike(BikeConstants.HALO_BIKE_ID, (BikeConstants.HALO_BIKE_NAME + BikeConstants.HALO_BIKE_ID++), 0, BikeConstants.NO_BORROW);
            bikeMo[index] = new MoBaiBike(BikeConstants.MO_BIKE_ID, (BikeConstants.MO_BIKE_NAME + BikeConstants.MO_BIKE_ID++), 0, BikeConstants.NO_BORROW);
        }

        //初始化单车公司信息
        bikeCompanies = new ShareBikeCompany[3];
        bikeCompanies[0] = new ShareBikeCompany(1, BikeConstants.OFO_COMPANY, bikeOfo, 5, 10);
        bikeCompanies[1] = new ShareBikeCompany(2, BikeConstants.HALO_COMPANY, bikeHalo, 5, 30);
        bikeCompanies[2] = new ShareBikeCompany(3, BikeConstants.MO_COMPANY, bikeMo, 5, 20);

    }

    //开始菜单
    public void startMenu() {
        System.out.println("欢迎使用迷你共享单车管理系统:");
        System.out.println("+++++++++++++++++++++++++++++++++++++");
        System.out.println("++          1." + BikeConstants.ADD_BIKE + "            ++");
        System.out.println("++          2." + BikeConstants.SELECT_BIKE + "            ++");
        System.out.println("++          3." + BikeConstants.DELETE_BIKE + "            ++");
        System.out.println("++          4." + BikeConstants.BORROW_BIKE + "            ++");
        System.out.println("++          5." + BikeConstants.RETURN_BIKE + "            ++");
        System.out.println("++          6." + BikeConstants.SORT_BIKE + "            ++");
        System.out.println("++          7.退      出            ++");
        System.out.println("++++++++++++++++++++++++++++++++++++++");
        System.out.print("请选择: ");
        int menuNum = input.nextInt();

        switch (menuNum) {
            case 1:
                addBike();
                break;
            case 2:
                selectBike();
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
                System.out.println("系统退出");
                return;
            default:
                System.out.println("输入非法");

        }

        returnMenu();
    }

    //单车排序
    private void sortBike() {
        System.out.println(BikeConstants.NAVIGATION + BikeConstants.RETURN_BIKE);
        System.out.println();
        bikeDao.sortBike(bikeCompanies);

    }

    //归还单车
    private void returnBike() {
        System.out.println(BikeConstants.NAVIGATION + BikeConstants.RETURN_BIKE);
        System.out.println();
        int choose = chooseBike();
        bikeDao.returnBike(bikeCompanies[choose - 1], input);

    }


    //借出单车
    private void borrowBike() {
        System.out.println(BikeConstants.NAVIGATION + BikeConstants.BORROW_BIKE);
        System.out.println();
        int choose = chooseBike();
        bikeDao.borrowBike(bikeCompanies[choose - 1], input);
    }

    //删除单车信息
    private void deleteBike() {
        System.out.println(BikeConstants.NAVIGATION + BikeConstants.DELETE_BIKE);
        System.out.println();
        int choose = chooseBike();
        bikeDao.deleteBike(bikeCompanies[choose - 1], input);
    }

    //查看单车
    private void selectBike() {
        System.out.println(BikeConstants.NAVIGATION + BikeConstants.SELECT_BIKE);
        System.out.println();
        bikeDao.selectBike(bikeCompanies);
    }


    //投放单车
    private void addBike() {
        System.out.println();
        System.out.println(BikeConstants.NAVIGATION + BikeConstants.ADD_BIKE);
        System.out.println();
        //获得要操作的单车
        int choose = chooseBike();
        if (choose < 0 || choose > 3) {
            System.out.println();
            System.out.println("  ---------------------");
            System.out.println("  |  该种类单车未入库   |");
            System.out.println("  ---------------------");
            addBike();
        } else {
            System.out.print("请输入要投放的数量: ");
            int addNum = input.nextInt();
            bikeDao.addBike(addNum, bikeCompanies[choose - 1]);
        }
    }

    //单车菜单
    private int chooseBike() {
        System.out.println("单车公司详情菜单: ");
        System.out.println("      1 " + BikeConstants.OFO_BIKE_NAME);
        System.out.println("      2 " + BikeConstants.HALO_BIKE_NAME);
        System.out.println("      3 " + BikeConstants.MO_BIKE_NAME);
        System.out.println("-------------------------------------->");
        System.out.print("请输入要操作的单车公司: ");
        return input.nextInt();
    }

    //返回菜单
    private void returnMenu() {
        System.out.print("输入0返回");
        int zero = input.nextInt();
        if (zero == 0) {
            startMenu();
        } else {
            System.out.println("系统退出");
            return;
        }
    }


}
