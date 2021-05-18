package com.zhangci.service.impl;

import com.zhangci.common.ServiceResponse;
import com.zhangci.dao.RankDao;
import com.zhangci.dao.impl.RankDaoImpl;
import com.zhangci.service.RankService;
import com.zhangci.util.InputUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * ClassName: RankServiceImpl
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/16 22:49
 * Version: 0.1
 * Since: JDK 1.8
 */
public class RankServiceImpl implements RankService {

    private static RankDao rankDao;
    private static InputUtil inputUtil;
    private static Integer monthNum;

    static {
        rankDao = new RankDaoImpl();
        inputUtil = new InputUtil();
        monthNum = 0;
    }

    @Override
    public ServiceResponse topTenAndTotalSalMonth() {
        System.out.print("请输入要查询的月份: ");
        monthNum = inputUtil.nextInt();

        //返回的是一个list集合
        List<Map<String, Object>> mapList = rankDao.selectSalesTopTen(monthNum);
        BigDecimal totalSales = rankDao.selectTotalSales(monthNum);

        //判断是否有结果集返回
        if (mapList.isEmpty()) {
            return ServiceResponse.error(monthNum + " 月份没有销售量");
        }
        showRankTopTen(mapList, totalSales);
        return ServiceResponse.success(monthNum + " 月份销售量查询成功");
    }

    /**
     * 展示商品销量前十的排名
     *
     * @param mapList    销量前十的商品数据
     * @param totalSales 本月总销量
     */
    private void showRankTopTen(List<Map<String, Object>> mapList, BigDecimal totalSales) {

        System.out.println(" ____________________________________________________");
        System.out.println("|  [ " + monthNum + "月 ] 销  量  排  行  榜    (Top：10)");
        System.out.println("|____________________________________________________");
        System.out.println("|        商品类别         商品本月销量                 ");
        System.out.println("|____________________________________________________");

        //list集合的嵌套查询
        for (Map<String, Object> map : mapList) {
            System.out.print("|");
            for (String str : map.keySet()) {
                System.out.print("          " + map.get(str) + "    ");
            }
            System.out.println();
        }
        System.out.println("|____________________________________________________");
        System.out.println("|     " + monthNum + "月  总销量 :  " + totalSales);
        System.out.println("|____________________________________________________");
    }
}
