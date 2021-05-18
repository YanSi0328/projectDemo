package com.zhangci02.dao.impl;

import com.zhangci02.consts.BikeConstants;
import com.zhangci02.dao.BikeDao;
import com.zhangci02.entity.*;
import com.zhangci02.util.CalculateTime;

import java.util.Arrays;
import java.util.Scanner;

/**
 * ClassName: BikeDaoImpl
 * <p>
 * Author: ZhangCi
 * Description: 业务逻辑类
 * Date: 2021/4/1 8:14
 * Version: 0.1
 * Since: JDK 1.8
 */
public class BikeDaoImpl implements BikeDao {


    /**
     * 查看单车
     *
     * @param bikeCompanies 公司里面存放的单车信息
     */
    @Override
    public void selectBike(ShareBikeCompany[] bikeCompanies) {
        //获取单车公司信息
        for (ShareBikeCompany bikeCompany : bikeCompanies) {
            System.out.println("+---------+--------------+-------------+-------------+");
            System.out.println("| 公司序号 |  公 司 名 称  | 公司单车数量 | 单车借出次数 |");
            System.out.println("+----------------------------------------------------+");
            System.out.println("|     " + bikeCompany.getBikeCompanyId() + "   | " +
                    bikeCompany.getBikeCompanyName() + " |     " + bikeCompany.getTotal()
                    + "       |      " + bikeCompany.getLendTimes() + "     |");
            System.out.println("+----------------------------------------------------+");
            showBikeInfo(bikeCompany);
            System.out.println();
        }

    }

    /**
     * 投放单车
     *
     * @param addNum      投放的单车数量
     * @param bikeCompany 单车公司
     */
    @Override
    public void addBike(int addNum, ShareBikeCompany bikeCompany) {
        //拿到公司单车
        ShareBike[] bikes = bikeCompany.getShareBikes();
        //数组扩容
        int total = bikeCompany.getTotal();
        if ((addNum + total) > bikes.length) {
            bikes = Arrays.copyOf(bikes, (bikes.length * 10));
        }
        //遍历单车数组
        for (int index = 0; index < addNum; index++) {
            //放到那个公司的单车数组里面？
            switch (bikeCompany.getBikeCompanyId()) {
                case 1:
                    //索引 = 具体单车信息
                    bikes[total + index] = new OfoBike(BikeConstants.OFO_BIKE_ID, (BikeConstants.OFO_BIKE_NAME + BikeConstants.OFO_BIKE_ID++), 0, null);
                    break;
                case 2:
                    bikes[total + index] = new HaloBike(BikeConstants.HALO_BIKE_ID, (BikeConstants.HALO_BIKE_NAME + BikeConstants.HALO_BIKE_ID++), 0, null);
                    break;
                case 3:
                    bikes[total + index] = new MoBaiBike(BikeConstants.MO_BIKE_ID, (BikeConstants.MO_BIKE_NAME + BikeConstants.MO_BIKE_ID++), 0, null);
                    break;
            }

        }
        //更新单车数据
        bikeCompany.setTotal(bikeCompany.getTotal() + addNum);
        bikeCompany.setShareBikes(bikes);
        System.out.println("投放 " + bikeCompany.getBikeCompanyName() + " 单车 " + addNum + " 辆");
    }

    /**
     * 删除单车
     *
     * @param bikeCompany 单车公司
     */
    @Override
    public void deleteBike(ShareBikeCompany bikeCompany, Scanner input) {
        System.out.println("此 " + bikeCompany.getBikeCompanyName() + "共有共享单车如下:");
        showBikeInfo(bikeCompany);

        System.out.println();
        System.out.print("请录入要删除的单车编号: ");
        int bikeId = input.nextInt();

        Integer total = bikeCompany.getTotal();
        ShareBike[] bikes = bikeCompany.getShareBikes();
        //循环遍历比较单车编号
        for (Integer index = 0; index < total; index++) {
            //拿到指定单车公司
            ShareBike bike = bikes[index];
            if (bike.getBikeId().equals(bikeId)) {
                //找到指定单车了，该进行什么操作？
                if (bike.getBikeStatus() == 1) {
                    System.out.println("编号为: " + bikeId + "的单车已经借出，无法删除");
                    return;
                }
                //找到了，也可以删除
                //先获得当前位置索引，然后使用后面元素替换前面的元素
                for (int i = index; i < total - 1; i++) {
                    bikes[i] = bikes[i + 1];
                }
                //最后一个索引位置元素改为null
                bikes[total - 1] = null;
                //更新单车信息
                bikeCompany.setTotal(total - 1);
                System.out.println("编号为: " + bikeId + " 的单车信息已经删除。");
                return;
            }
        }
        System.out.println("不存在编号为 " + bikeId + " 单车，无法删除的");
    }

    /**
     * 借出单车
     *
     * @param bikeCompany 单车公司
     * @param input       获得输入的单车编号
     */
    @Override
    public void borrowBike(ShareBikeCompany bikeCompany, Scanner input) {
        System.out.println("此 " + bikeCompany.getBikeCompanyName() + "共有共享单车如下:");
        showBikeInfo(bikeCompany);
        System.out.println();
        System.out.print("请录入要借出的单车编号: ");
        int bikeId = input.nextInt();

        Integer total = bikeCompany.getTotal();
        ShareBike[] bikes = bikeCompany.getShareBikes();
        //比对单车信息

        //循环遍历比较单车编号
        for (Integer index = 0; index < total; index++) {
            //拿到指定单车公司
            ShareBike bike = bikes[index];
            if (bike.getBikeId().equals(bikeId)) {
                //找到指定单车了，该进行什么操作？
                if (bike.getBikeStatus() == 1) {
                    System.out.println("编号为: " + bikeId + "的单车已经借出，无法借出");
                    return;
                }
                //找到了，修改借出日期和单车状态
                System.out.print("请录入借出日期" + BikeConstants.PATTERN + ":");
                input.nextLine();
                String borrowTime = input.nextLine();
                bike.setBikeStatus(1);
                bike.setBorrowTime(borrowTime);

                //更新单车信息
                bikeCompany.setLendTimes(bikeCompany.getLendTimes() + 1);

                System.out.println("借出 " + bikeCompany.getBikeCompanyName() + " 下的 " + bike.getBikeName() + " 成功");
            }
        }
    }

    /**
     * 归还单车
     *
     * @param bikeCompany 要归还的单车公司
     * @param input       获取输入的单车编号
     */
    @Override
    public void returnBike(ShareBikeCompany bikeCompany, Scanner input) {
        System.out.println("此 " + bikeCompany.getBikeCompanyName() + "共有共享单车如下:");
        showBikeInfo(bikeCompany);
        System.out.println();
        System.out.print("请录入要归还的单车编号: ");
        Integer bikeId = input.nextInt();
        //获得指定单车公司的单车总量
        ShareBike[] bikes = bikeCompany.getShareBikes();
        //判断该编号的单车是否存在
        for (Integer index = 0; index < bikeCompany.getTotal(); index++) {
            //获得指定单车
            ShareBike bike = bikes[index];
            //单车是否借出
            if (bike.getBikeStatus() == 0) {
                System.out.println("编号为: " + bikeId + "的单车未借出");
                return;
            }
            //单车已经借出，判断单车编号是否正确
            if (bikeId.equals(bike.getBikeId())) {
                //单车编号正确，修改单车状态、录入借出时间、统计借出数量
                System.out.print("请录入归还时间: ");
                input.nextLine();
                String returnTime = input.nextLine();
                System.out.print("您的借出时间: " + bike.getBorrowTime());

                //结算时间
                int time = new CalculateTime().calTime(bike.getBorrowTime(), returnTime);
                System.out.println("用车时间为 " + time + " 小时，需要支付: " + time + " 元");

                //更新单车信息
                bikeCompany.setLendTimes(bikeCompany.getLendTimes() + 1);
                bike.setBikeStatus(0);
                bike.setBorrowTime(null);
                //找到

            }

        }

    }

    /**
     * 单车排序
     *
     * @param bikeCompanies 公司
     */
    @Override
    public void sortBike(ShareBikeCompany[] bikeCompanies) {
        System.out.println(" 公 司 名 称\t\t\t借车次数");
        //获得三个单车公司的借出次数
        int len = bikeCompanies.length;
        //冒泡排序
        for (int index = 0; index < len - 1; index++) {
            for (int j = index + 1; j < len; j++) {
                //判断前一个元素与后一个元素的大小关系
                if (bikeCompanies[index].getLendTimes() < bikeCompanies[j].getLendTimes()) {
                    ShareBikeCompany temp = bikeCompanies[index];
                    bikeCompanies[index] = bikeCompanies[j];
                    bikeCompanies[j] = temp;
                }
            }
        }
        //遍历输出公司
        for (ShareBikeCompany bikeCompany : bikeCompanies) {
            System.out.println(bikeCompany.getBikeCompanyName() + "\t\t" + bikeCompany.getLendTimes());
        }


    }

    /**
     * 显示单车信息
     *
     * @param bikeCompany 具体公司的单车信息
     */
    private void showBikeInfo(ShareBikeCompany bikeCompany) {
        //获得单车对象
        ShareBike[] shareBikes = bikeCompany.getShareBikes();
        System.out.println("--------------------------------------------------------------");
        System.out.println("单车序号\t\t单  车  品  牌\t\t单车状态\t\t借出时间");
        //这个地方不能用数组长度？
        //因为数组后期会扩容
        for (int index = 0; index < bikeCompany.getTotal(); index++) {
            ShareBike bike = shareBikes[index];
            //输出信息之前进行判断(三目运算符)
            String bikeStatus = (bike.getBikeStatus() == 0) ? "可借" : "不可借";
            String bikeBorrowTime = (bike.getBorrowTime() == null) ? "" : bike.getBorrowTime();
            System.out.println(bike.getBikeId() + "\t\t" + bike.getBikeName() + "\t\t"
                    + bikeStatus + "\t\t" + bikeBorrowTime);
        }
        System.out.println("--------------------------------------------------------------");

    }


}
