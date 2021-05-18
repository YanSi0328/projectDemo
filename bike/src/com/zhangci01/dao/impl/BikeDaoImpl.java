package com.zhangci01.dao.impl;

import com.zhangci01.consts.BikeConstants;
import com.zhangci01.dao.BikeDao;
import com.zhangci01.entity.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/**
 * ClassName: BikeDaoImpl
 * <p>
 * Author: ZhangCi
 * Description: 单车接口实现类 只写业务逻辑
 * Date: 2021/3/26 8:31
 * Version: 0.1
 * Since: JDK 1.8
 */
public class BikeDaoImpl implements BikeDao {

    //投放单车
    @Override
    public void addBike(int addNum, ShareBikeCompany company) {

        //数组存放单车数量
        ShareBike[] bikes = company.getSharedBikes();
        //判断空间
        int sum = company.getSum();
        if ((sum + addNum) > bikes.length) {
            //数组扩容
            bikes = Arrays.copyOf(bikes, (bikes.length) * 10);
        }
        //单车数量放入数组
        for (int i = 1; i <= addNum; i++) {
            switch (company.getBikeCompanyId()) {
                case 1:
                    bikes[sum + (i - 1)] = new OfoBike((sum + i), BikeConstants.OFO_BIKE_NAME + (sum + i), 1, null);
                    break;
                case 2:
                    bikes[sum + (i - 1)] = new HaloBike((sum + i), BikeConstants.HALO_BIKE_NAME + (sum + i), 1, null);
                    break;
                case 3:
                    bikes[sum + (i - 1)] = new MoBike((sum + i), BikeConstants.MO_BIKE_NAME + (sum + i), 1, null);
                    break;
                default:
                    System.out.println("该单车公司不存在！");
            }
        }
        //更新单车总量
        company.setSum(company.getSum() + addNum);
        company.setSharedBikes(bikes);
        System.out.println("投放 " + company.getBikeCompanyName() + " 单车量 " + addNum + " 成功");

        System.out.println("----------------------");
    }


    //查看单车
    @Override
    public void selectBike(ShareBikeCompany[] shareBikeCompanies) {
        System.out.println(BikeConstants.MAIN_MENU_NAVIGATION + BikeConstants.MAIN_MENU_SELBIKE);
        System.out.println();
        //获取单车公司的单车信息
        for (ShareBikeCompany bikeCompany : shareBikeCompanies) {
            //显示菜单
            System.out.println("+---------+---------+-------------+-----------------+");
            System.out.println("| 公司序号 | 公司名称 | 公司单车数量 | 公司单车借出次数 |");
            System.out.println("+---------------------------------------------------+");
            System.out.println("|     " + bikeCompany.getBikeCompanyId() + "   | " +
                    bikeCompany.getBikeCompanyName() + " |     " + bikeCompany.getSum()
                    + "       |        " + bikeCompany.getCount() + "       |");
            System.out.println("+---------------------------------------------------+");
            showCompanyBike(bikeCompany);

            System.out.println();
        }
    }

    //展示公司单车信息
    private void showCompanyBike(ShareBikeCompany bikeCompany) {
        ShareBike[] shareBikes = bikeCompany.getSharedBikes();
        System.out.println("单车序号\t  单车品牌  \t单车状态\t借出时间");
        for (int i = 0; i < bikeCompany.getSum(); i++) {
            //获取状态
            ShareBike bike = shareBikes[i];
            String bikeStatus = (bike.getBikeStatus() == 1) ? "可借" : "不可借";
            String bikeBorrowTime = (bike.getBorrowTime() == null) ? "" : bike.getBorrowTime();
            System.out.println("   " + bike.getBikeId() + "     " + bike.getBikeName()
                    + "     " + bikeStatus + "   " + bikeBorrowTime);

        }
    }

    //删除单车:存在一个bug只能按序删除：
    //已解决，是数组索引与单车id不一致的问题导致的
    @Override
    public void deleteBike(ShareBikeCompany company, Scanner input) {
        System.out.println(company.getBikeCompanyName() + " 下的单车信息如下：");
        //显示当前公司下的单车信息
        showCompanyBike(company);

        //使用数组接收该公司的单车数量
        ShareBike[] bikes = company.getSharedBikes();

        //根据单车id删除单车信息
        boolean flag = false;//标志单车是否删除
        System.out.println("请录入要删除的单车id：");
        int bikeId = input.nextInt();
        //循环遍历删除
        for (int i = 0, sum = company.getSum(); i < sum; i++) {
            ShareBike bike = bikes[i];
            if (bikeId == bike.getBikeId()) {
                flag = true;
                //判断单车状态
                if (bike.getBikeStatus() == 0) {
                    System.out.println(bikeId + "单车已经借出，无法删除");
                    return;
                }
                //遍历删除，更改索引
                for (int j = i; j < sum - 1; j++) {
                    bikes[j] = bikes[j + 1];
                }
                bikes[sum - 1] = null;
                break;
            }
        }
        //判断单车是否可以删除
        if (!flag) {
            System.out.println("单车不存在，无法删除！");
            return;
        }
        company.setSum(company.getSum() - 1);
        System.out.println(bikeId + "删除成功");
    }


    //借出单车
    @Override
    public void borrowBike(ShareBikeCompany company, Scanner input) {
        System.out.println("此 " + company.getBikeCompanyName() + " 有共享单车如下: ");
        showCompanyBike(company);//显示单车公司信息
        System.out.println();
        //获得该公司的打车数量
        ShareBike[] bikes = company.getSharedBikes();
        System.out.print("请录入要借出的单车编号：");
        int borrId = input.nextInt();
        //根据id找到指定单车
        boolean borrowFlag = false;
        int sum = company.getSum();
        for (int index = 0; index < sum; index++) {
            //根据索引获得指定单车信息
            ShareBike bike = bikes[index];
            //判断是否是指定单车
            if (borrId == bike.getBikeId()) {
                borrowFlag = true;
                //是否可以借出
                if (bike.getBikeStatus() == 0) {
                    System.out.println(borrId + "已经被借出，请选择其他车辆");
                    return;
                }
                //可以借出单车
                System.out.print("请输入借出日期(yyyy/mm/dd HH:mm:ss): ");//2020/03/26 14:57:20
                input.nextLine();
                String borrowTime = input.nextLine();
                bike.setBikeStatus(0);
                bike.setBorrowTime(borrowTime);
                break;
            }
        }
        //单车不存在的判断
        if (!borrowFlag) {
            System.out.println(borrId + "单车不存在，无法借出！");
        }
        System.out.println("借出 " + company.getBikeCompanyName() + " 下的 " + borrId + "成功");
        company.setCount(company.getCount() - 1);
        System.out.println("-----------------------------------");

    }

    //归还单车
    @Override
    public void returnBike(ShareBikeCompany company, Scanner input) {
        ShareBike[] bikes = company.getSharedBikes();
        System.out.print("请录入要归还的单车编号: ");
        int returnBikeId = input.nextInt();
        boolean returnFlag = false;
        int sum = company.getSum();
        for (int index = 0; index < sum; index++) {
            //获得指定单车信息
            ShareBike bike = bikes[index];
            if (returnBikeId == bike.getBikeId()) {
                returnFlag = true;
                //单车状态
                if (bike.getBikeStatus() == 1) {
                    System.out.println("单车未借出，请确认后重新归还");
                    return;
                }
                //可以归还
                System.out.print("请录入归还时间(yyyy/mm/dd HH:mm:ss):");
                input.nextLine();
                String returnTime = input.nextLine();

                String borrowTime = bike.getBorrowTime();
                //时间的计算
                int hours = calTime(returnTime, borrowTime);
                System.out.println("您的借车时间为:" + borrowTime);
                System.out.println("用车时间为 《" + hours + "》 小时，需要支付:" + hours + " 元");

                //归还成功的标志
                bike.setBikeStatus(1);
                bike.setBorrowTime(null);
                break;
            }
        }
        if (!returnFlag) {
            System.out.println(returnBikeId + "单车不存在，请确认信息后再归还！");
            return;
        }
        System.out.println(returnBikeId + "归还成功");
        System.out.println("---------------------------------------------");
    }

    //计算时间
    private int calTime(String returnTime, String borrowTime) {

        int hour = 0;
        //格式化日期
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date returnDate = dateFormat.parse(returnTime);
            Date borrowDate = dateFormat.parse(borrowTime);
            long distanceTime = returnDate.getTime() - borrowDate.getTime();//单位是毫秒
            //最终时间:单位：小时
            hour = (int) (distanceTime / 1000 / 3600);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hour;
    }
}
