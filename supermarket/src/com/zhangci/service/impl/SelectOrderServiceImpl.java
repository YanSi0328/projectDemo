package com.zhangci.service.impl;

import com.zhangci.common.ServiceResponse;
import com.zhangci.dao.SelectOrderDao;
import com.zhangci.dao.impl.SelectOrderDaoImpl;
import com.zhangci.service.SelectOrderService;
import com.zhangci.util.InputUtil;
import java.util.List;
import java.util.Map;

/**
 * ClassName: SelOrdServiceImpl
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/16 18:00
 * Version: 0.1
 * Since: JDK 1.8
 */
public class SelectOrderServiceImpl implements SelectOrderService {

    private static SelectOrderDao selectOrderDao;
    private static InputUtil inputUtil;


    static {
        selectOrderDao = new SelectOrderDaoImpl();
        inputUtil = new InputUtil();
    }

    /**
     * 订单查询主界面
     */
    @Override
    public void selectMainMenu() {
        System.out.println("      1. 根据会员编号进行查询");
        System.out.println("      2. 根据订单编号进行查询");
        System.out.print("请输入菜单编号: ");
        int menuId = inputUtil.nextInt();
        switch (menuId) {
            case 1:
                selectOrderMsgByUid();
                break;
            case 2:
                selectOrderMsgBuOid();
                break;
            default:
                System.out.println("菜单编号错误，请确认后再次输入。");
                break;
        }
    }

    @Override
    public ServiceResponse selectOrderMsgByUid() {

        System.out.println("请输入要查询的会员编号: ");
        int Uid = inputUtil.nextInt();

        List<Map<String, Object>> maps = selectOrderDao.selectOrderMsgByUid(Uid);
        // 判断查询结果
        if (maps.isEmpty()) {
            return ServiceResponse.error("   会员编号 " + Uid + " 的消费记录不存在，请确认后再查询");
        }
        //展示信息
        showOrderMsg(maps);
        return ServiceResponse.success();
    }


    @Override
    public ServiceResponse selectOrderMsgBuOid() {

        System.out.println("请输入要查询的订单编号: ");
        int Oid = inputUtil.nextInt();
        List<Map<String, Object>> maps = selectOrderDao.selectOrderMsgBuOid(Oid);
        // 判断查询结果
        if (maps.isEmpty()) {
            return ServiceResponse.error("   订单编号 " + Oid + " 不存在，请确认后在查询");
        }
        showOrderMsg(maps);
        return ServiceResponse.success("查询成功");
    }

    /**
     * 展示订单信息
     *
     * @param maps 订单集合
     */
    private void showOrderMsg(List<Map<String, Object>> maps) {
        //订单编号，商品编号，购买数量，会员号，总金额，下单时间，支付方式

        System.out.println("___________________________________________________________________________________");
        System.out.println("|                  订         单         信        息         表                   |");
        System.out.println("|_________________________________________________________________________________|");
        System.out.println("|订单编号|商品编号|购买数量|会 员 号|总 金 额|  下单时间                |支付方式 |");
        System.out.println("|_________________________________________________________________________________|");

        for (Map<String, Object> map : maps) {
            for (String str : map.keySet()) {
                System.out.print("|   " + map.get(str) + "   ");
            }
            System.out.println("|");
        }
        System.out.println("|_________________________________________________________________________________|");
    }
}
