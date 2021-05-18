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
 * Description: ��������
 * Date: 2021/4/1 8:15
 * Version: 0.1
 * Since: JDK 1.8
 */
public class ShareBikeMgr {
    //ȫ�ֱ���
    private Scanner input;
    private BikeDao bikeDao;
    private ShareBikeCompany[] bikeCompanies;


    public ShareBikeMgr() {
        initial();
    }

    //��Ϣ��ʼ��
    private void initial() {
        input = new Scanner(System.in);
        bikeDao = new BikeDaoImpl();


        //��������ʵ������ʹ�������ŵ�����Ϣ
        ShareBike[] bikeOfo = new OfoBike[5];
        ShareBike[] bikeHalo = new HaloBike[5];
        ShareBike[] bikeMo = new MoBaiBike[5];

        //����Ϣ��������
        for (int index = 0; index < 5; index++) {
            bikeOfo[index] = new OfoBike(BikeConstants.OFO_BIKE_ID, (BikeConstants.OFO_BIKE_NAME + BikeConstants.OFO_BIKE_ID++), 1, "2021/04/02 8:00:00");
            bikeHalo[index] = new HaloBike(BikeConstants.HALO_BIKE_ID, (BikeConstants.HALO_BIKE_NAME + BikeConstants.HALO_BIKE_ID++), 0, BikeConstants.NO_BORROW);
            bikeMo[index] = new MoBaiBike(BikeConstants.MO_BIKE_ID, (BikeConstants.MO_BIKE_NAME + BikeConstants.MO_BIKE_ID++), 0, BikeConstants.NO_BORROW);
        }

        //��ʼ��������˾��Ϣ
        bikeCompanies = new ShareBikeCompany[3];
        bikeCompanies[0] = new ShareBikeCompany(1, BikeConstants.OFO_COMPANY, bikeOfo, 5, 10);
        bikeCompanies[1] = new ShareBikeCompany(2, BikeConstants.HALO_COMPANY, bikeHalo, 5, 30);
        bikeCompanies[2] = new ShareBikeCompany(3, BikeConstants.MO_COMPANY, bikeMo, 5, 20);

    }

    //��ʼ�˵�
    public void startMenu() {
        System.out.println("��ӭʹ�����㹲��������ϵͳ:");
        System.out.println("+++++++++++++++++++++++++++++++++++++");
        System.out.println("++          1." + BikeConstants.ADD_BIKE + "            ++");
        System.out.println("++          2." + BikeConstants.SELECT_BIKE + "            ++");
        System.out.println("++          3." + BikeConstants.DELETE_BIKE + "            ++");
        System.out.println("++          4." + BikeConstants.BORROW_BIKE + "            ++");
        System.out.println("++          5." + BikeConstants.RETURN_BIKE + "            ++");
        System.out.println("++          6." + BikeConstants.SORT_BIKE + "            ++");
        System.out.println("++          7.��      ��            ++");
        System.out.println("++++++++++++++++++++++++++++++++++++++");
        System.out.print("��ѡ��: ");
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
                System.out.println("ϵͳ�˳�");
                return;
            default:
                System.out.println("����Ƿ�");

        }

        returnMenu();
    }

    //��������
    private void sortBike() {
        System.out.println(BikeConstants.NAVIGATION + BikeConstants.RETURN_BIKE);
        System.out.println();
        bikeDao.sortBike(bikeCompanies);

    }

    //�黹����
    private void returnBike() {
        System.out.println(BikeConstants.NAVIGATION + BikeConstants.RETURN_BIKE);
        System.out.println();
        int choose = chooseBike();
        bikeDao.returnBike(bikeCompanies[choose - 1], input);

    }


    //�������
    private void borrowBike() {
        System.out.println(BikeConstants.NAVIGATION + BikeConstants.BORROW_BIKE);
        System.out.println();
        int choose = chooseBike();
        bikeDao.borrowBike(bikeCompanies[choose - 1], input);
    }

    //ɾ��������Ϣ
    private void deleteBike() {
        System.out.println(BikeConstants.NAVIGATION + BikeConstants.DELETE_BIKE);
        System.out.println();
        int choose = chooseBike();
        bikeDao.deleteBike(bikeCompanies[choose - 1], input);
    }

    //�鿴����
    private void selectBike() {
        System.out.println(BikeConstants.NAVIGATION + BikeConstants.SELECT_BIKE);
        System.out.println();
        bikeDao.selectBike(bikeCompanies);
    }


    //Ͷ�ŵ���
    private void addBike() {
        System.out.println();
        System.out.println(BikeConstants.NAVIGATION + BikeConstants.ADD_BIKE);
        System.out.println();
        //���Ҫ�����ĵ���
        int choose = chooseBike();
        if (choose < 0 || choose > 3) {
            System.out.println();
            System.out.println("  ---------------------");
            System.out.println("  |  �����൥��δ���   |");
            System.out.println("  ---------------------");
            addBike();
        } else {
            System.out.print("������ҪͶ�ŵ�����: ");
            int addNum = input.nextInt();
            bikeDao.addBike(addNum, bikeCompanies[choose - 1]);
        }
    }

    //�����˵�
    private int chooseBike() {
        System.out.println("������˾����˵�: ");
        System.out.println("      1 " + BikeConstants.OFO_BIKE_NAME);
        System.out.println("      2 " + BikeConstants.HALO_BIKE_NAME);
        System.out.println("      3 " + BikeConstants.MO_BIKE_NAME);
        System.out.println("-------------------------------------->");
        System.out.print("������Ҫ�����ĵ�����˾: ");
        return input.nextInt();
    }

    //���ز˵�
    private void returnMenu() {
        System.out.print("����0����");
        int zero = input.nextInt();
        if (zero == 0) {
            startMenu();
        } else {
            System.out.println("ϵͳ�˳�");
            return;
        }
    }


}
