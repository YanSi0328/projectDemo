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
 * Description: ҵ���߼���
 * Date: 2021/4/1 8:14
 * Version: 0.1
 * Since: JDK 1.8
 */
public class BikeDaoImpl implements BikeDao {


    /**
     * �鿴����
     *
     * @param bikeCompanies ��˾�����ŵĵ�����Ϣ
     */
    @Override
    public void selectBike(ShareBikeCompany[] bikeCompanies) {
        //��ȡ������˾��Ϣ
        for (ShareBikeCompany bikeCompany : bikeCompanies) {
            System.out.println("+---------+--------------+-------------+-------------+");
            System.out.println("| ��˾��� |  �� ˾ �� ��  | ��˾�������� | ����������� |");
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
     * Ͷ�ŵ���
     *
     * @param addNum      Ͷ�ŵĵ�������
     * @param bikeCompany ������˾
     */
    @Override
    public void addBike(int addNum, ShareBikeCompany bikeCompany) {
        //�õ���˾����
        ShareBike[] bikes = bikeCompany.getShareBikes();
        //��������
        int total = bikeCompany.getTotal();
        if ((addNum + total) > bikes.length) {
            bikes = Arrays.copyOf(bikes, (bikes.length * 10));
        }
        //������������
        for (int index = 0; index < addNum; index++) {
            //�ŵ��Ǹ���˾�ĵ����������棿
            switch (bikeCompany.getBikeCompanyId()) {
                case 1:
                    //���� = ���嵥����Ϣ
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
        //���µ�������
        bikeCompany.setTotal(bikeCompany.getTotal() + addNum);
        bikeCompany.setShareBikes(bikes);
        System.out.println("Ͷ�� " + bikeCompany.getBikeCompanyName() + " ���� " + addNum + " ��");
    }

    /**
     * ɾ������
     *
     * @param bikeCompany ������˾
     */
    @Override
    public void deleteBike(ShareBikeCompany bikeCompany, Scanner input) {
        System.out.println("�� " + bikeCompany.getBikeCompanyName() + "���й���������:");
        showBikeInfo(bikeCompany);

        System.out.println();
        System.out.print("��¼��Ҫɾ���ĵ������: ");
        int bikeId = input.nextInt();

        Integer total = bikeCompany.getTotal();
        ShareBike[] bikes = bikeCompany.getShareBikes();
        //ѭ�������Ƚϵ������
        for (Integer index = 0; index < total; index++) {
            //�õ�ָ��������˾
            ShareBike bike = bikes[index];
            if (bike.getBikeId().equals(bikeId)) {
                //�ҵ�ָ�������ˣ��ý���ʲô������
                if (bike.getBikeStatus() == 1) {
                    System.out.println("���Ϊ: " + bikeId + "�ĵ����Ѿ�������޷�ɾ��");
                    return;
                }
                //�ҵ��ˣ�Ҳ����ɾ��
                //�Ȼ�õ�ǰλ��������Ȼ��ʹ�ú���Ԫ���滻ǰ���Ԫ��
                for (int i = index; i < total - 1; i++) {
                    bikes[i] = bikes[i + 1];
                }
                //���һ������λ��Ԫ�ظ�Ϊnull
                bikes[total - 1] = null;
                //���µ�����Ϣ
                bikeCompany.setTotal(total - 1);
                System.out.println("���Ϊ: " + bikeId + " �ĵ�����Ϣ�Ѿ�ɾ����");
                return;
            }
        }
        System.out.println("�����ڱ��Ϊ " + bikeId + " �������޷�ɾ����");
    }

    /**
     * �������
     *
     * @param bikeCompany ������˾
     * @param input       �������ĵ������
     */
    @Override
    public void borrowBike(ShareBikeCompany bikeCompany, Scanner input) {
        System.out.println("�� " + bikeCompany.getBikeCompanyName() + "���й���������:");
        showBikeInfo(bikeCompany);
        System.out.println();
        System.out.print("��¼��Ҫ����ĵ������: ");
        int bikeId = input.nextInt();

        Integer total = bikeCompany.getTotal();
        ShareBike[] bikes = bikeCompany.getShareBikes();
        //�ȶԵ�����Ϣ

        //ѭ�������Ƚϵ������
        for (Integer index = 0; index < total; index++) {
            //�õ�ָ��������˾
            ShareBike bike = bikes[index];
            if (bike.getBikeId().equals(bikeId)) {
                //�ҵ�ָ�������ˣ��ý���ʲô������
                if (bike.getBikeStatus() == 1) {
                    System.out.println("���Ϊ: " + bikeId + "�ĵ����Ѿ�������޷����");
                    return;
                }
                //�ҵ��ˣ��޸Ľ�����ں͵���״̬
                System.out.print("��¼��������" + BikeConstants.PATTERN + ":");
                input.nextLine();
                String borrowTime = input.nextLine();
                bike.setBikeStatus(1);
                bike.setBorrowTime(borrowTime);

                //���µ�����Ϣ
                bikeCompany.setLendTimes(bikeCompany.getLendTimes() + 1);

                System.out.println("��� " + bikeCompany.getBikeCompanyName() + " �µ� " + bike.getBikeName() + " �ɹ�");
            }
        }
    }

    /**
     * �黹����
     *
     * @param bikeCompany Ҫ�黹�ĵ�����˾
     * @param input       ��ȡ����ĵ������
     */
    @Override
    public void returnBike(ShareBikeCompany bikeCompany, Scanner input) {
        System.out.println("�� " + bikeCompany.getBikeCompanyName() + "���й���������:");
        showBikeInfo(bikeCompany);
        System.out.println();
        System.out.print("��¼��Ҫ�黹�ĵ������: ");
        Integer bikeId = input.nextInt();
        //���ָ��������˾�ĵ�������
        ShareBike[] bikes = bikeCompany.getShareBikes();
        //�жϸñ�ŵĵ����Ƿ����
        for (Integer index = 0; index < bikeCompany.getTotal(); index++) {
            //���ָ������
            ShareBike bike = bikes[index];
            //�����Ƿ���
            if (bike.getBikeStatus() == 0) {
                System.out.println("���Ϊ: " + bikeId + "�ĵ���δ���");
                return;
            }
            //�����Ѿ�������жϵ�������Ƿ���ȷ
            if (bikeId.equals(bike.getBikeId())) {
                //���������ȷ���޸ĵ���״̬��¼����ʱ�䡢ͳ�ƽ������
                System.out.print("��¼��黹ʱ��: ");
                input.nextLine();
                String returnTime = input.nextLine();
                System.out.print("���Ľ��ʱ��: " + bike.getBorrowTime());

                //����ʱ��
                int time = new CalculateTime().calTime(bike.getBorrowTime(), returnTime);
                System.out.println("�ó�ʱ��Ϊ " + time + " Сʱ����Ҫ֧��: " + time + " Ԫ");

                //���µ�����Ϣ
                bikeCompany.setLendTimes(bikeCompany.getLendTimes() + 1);
                bike.setBikeStatus(0);
                bike.setBorrowTime(null);
                //�ҵ�

            }

        }

    }

    /**
     * ��������
     *
     * @param bikeCompanies ��˾
     */
    @Override
    public void sortBike(ShareBikeCompany[] bikeCompanies) {
        System.out.println(" �� ˾ �� ��\t\t\t�賵����");
        //�������������˾�Ľ������
        int len = bikeCompanies.length;
        //ð������
        for (int index = 0; index < len - 1; index++) {
            for (int j = index + 1; j < len; j++) {
                //�ж�ǰһ��Ԫ�����һ��Ԫ�صĴ�С��ϵ
                if (bikeCompanies[index].getLendTimes() < bikeCompanies[j].getLendTimes()) {
                    ShareBikeCompany temp = bikeCompanies[index];
                    bikeCompanies[index] = bikeCompanies[j];
                    bikeCompanies[j] = temp;
                }
            }
        }
        //���������˾
        for (ShareBikeCompany bikeCompany : bikeCompanies) {
            System.out.println(bikeCompany.getBikeCompanyName() + "\t\t" + bikeCompany.getLendTimes());
        }


    }

    /**
     * ��ʾ������Ϣ
     *
     * @param bikeCompany ���幫˾�ĵ�����Ϣ
     */
    private void showBikeInfo(ShareBikeCompany bikeCompany) {
        //��õ�������
        ShareBike[] shareBikes = bikeCompany.getShareBikes();
        System.out.println("--------------------------------------------------------------");
        System.out.println("�������\t\t��  ��  Ʒ  ��\t\t����״̬\t\t���ʱ��");
        //����ط����������鳤�ȣ�
        //��Ϊ������ڻ�����
        for (int index = 0; index < bikeCompany.getTotal(); index++) {
            ShareBike bike = shareBikes[index];
            //�����Ϣ֮ǰ�����ж�(��Ŀ�����)
            String bikeStatus = (bike.getBikeStatus() == 0) ? "�ɽ�" : "���ɽ�";
            String bikeBorrowTime = (bike.getBorrowTime() == null) ? "" : bike.getBorrowTime();
            System.out.println(bike.getBikeId() + "\t\t" + bike.getBikeName() + "\t\t"
                    + bikeStatus + "\t\t" + bikeBorrowTime);
        }
        System.out.println("--------------------------------------------------------------");

    }


}
