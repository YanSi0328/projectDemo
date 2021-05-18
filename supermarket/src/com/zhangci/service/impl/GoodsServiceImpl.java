package com.zhangci.service.impl;


import com.zhangci.common.ServiceResponse;
import com.zhangci.dao.GoodsDao;
import com.zhangci.dao.impl.GoodsDaoImpl;
import com.zhangci.entity.Goods;
import com.zhangci.service.GoodsService;

import java.time.LocalDateTime;
import java.util.*;


/**
 * ClassName: GoodsServiceImpl
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/13 21:02
 * Version: 0.1
 * Since: JDK 1.8
 */
public class GoodsServiceImpl implements GoodsService {

    private static GoodsDao goodsDao;
    private static Scanner input;

    static {
        goodsDao = new GoodsDaoImpl();
        input = new Scanner(System.in);
    }

    @Override
    public ServiceResponse addGoods(Goods goods) {
        //新增之前查询商品表
        selectGoods();

        //获得用户插入的商品信息
        System.out.println("商品信息录入格式: 商品名称,商品类型,新增数量,单价,状态(1-正常，2-下架，3-删除),折扣");
        System.out.print("请输入商品各项信息：");
        String line = input.nextLine();
        String[] split = line.split(",");

        //取出数组元素，并转换为相对应的类型
        int goodsNum = Integer.parseInt(split[2]);
        float goodsPrince = Float.parseFloat(split[3]);
        int goodsStatus = Integer.parseInt(split[4]);
        float goodsDiscount = Float.parseFloat(split[5]);

        //测试信息：苹果,水果,30,5,1,10
        goods = new Goods(split[0], split[1],
                goodsNum, goodsPrince, goodsStatus,
                goodsDiscount, LocalDateTime.now(), null);

        //将信息传递回到dao层,dao层在再将数据写回到数据库
        int record = goodsDao.addGoods(goods);
        if (record == 0) {
            return ServiceResponse.error();
        }
        return ServiceResponse.success();
    }

    @Override
    public ServiceResponse updateGoods(Goods goods) {
        //修改数据之前先查询
        selectGoods();

        System.out.print("请录入要修改的商品编号: ");
        int goodsId = input.nextInt();

        //创建集合存储要修改的键值对
        Map<String, Object> goodMap = new HashMap<>(16);
        System.out.println("商品信息录入格式: 商品名称,商品类型,新增数量,单价,状态(1-正常，2-下架，3-删除),折扣");
        System.out.print("请输入商品各项信息：");
        input.nextLine();
        String line = input.nextLine();
        String[] split = line.split(",");

        //将集合键值对与录入的信息进行匹配
        goodMap.put("goods_name", split[0]);
        goodMap.put("goods_type", split[1]);
        goodMap.put("goods_num", split[2]);
        goodMap.put("goods_price", split[3]);
        goodMap.put("goods_status", split[4]);
        goodMap.put("goods_discount", split[5]);

        int record = goodsDao.updateGoods(goodMap, goodsId);
        if (record == 0) {
            return ServiceResponse.error();
        }
        return ServiceResponse.success();

    }


    @Override
    public void selectGoods() {
        //拿到商品集合
        List<Goods> goodsList = goodsDao.selectAllGoods();

        //输出商品表信息
        System.out.println("|————————————————————————————————————————————————————————————————————————————————————————————————|");
        System.out.println("|                                       商   品   信   息   表                                    |");
        System.out.println("|________________________________________________________________________________________________|");
        System.out.println("| 编号   名称    类型    数量 单价(元) 状态   折扣(%)    创 建 时 间           更 新 时 间            |");
        System.out.println("|________________________________________________________________________________________________|");

        for (Goods goods : goodsList) {
            showGoods(goods);
        }
        System.out.println("|________________________________________________________________________________________________|");
        System.out.println();
        System.out.println("=======>>>  查询完毕~~~");
    }

    @Override
    public void selectGoodsById() {
        System.out.println("请录入要查询的商品id: ");
        int goodsId = input.nextInt();
        Goods goods = goodsDao.selectGoodsById(goodsId);

        System.out.println("| 编号   |名称    |类型    |数量 |单价(元) |状态   |折扣(%)    |创 建 时 间           |更 新 时 间");

        showGoods(goods);

        System.out.println("=======>>>  查询完毕~~~");
    }

    @Override
    public ServiceResponse deleteGoodsById() {
        //删除之前查询商品表
        selectGoods();
        //删除操作
        System.out.println();
        System.out.print("请录入要删除的商品id: ");
        int goodsId = input.nextInt();
        int result = goodsDao.deleteGoodsById(goodsId);
        if (result != 1) {
            return ServiceResponse.error("操作非法");
        }
        //显示删除后的商品表
        selectGoods();
        return ServiceResponse.success(goodsId + " 的商品删除成功(Service层)");
    }


    //展示商品信息
    public ServiceResponse showGoods(Goods goods) {
        if (Objects.equals(goods, null)) {
            return ServiceResponse.error("商品信息为空无法展示");
        }
        Objects.requireNonNull(goods);
        String goodsStatusMsg = null;
        //商品状态码的处理 1-正常，2-下架，3-删除
        int goodsStatus = goods.getGoodsStatus();
        switch (goodsStatus) {
            case 1:
                goodsStatusMsg = "正常";
                break;
            case 2:
                goodsStatusMsg = "下架";
                break;
            case 3:
                goodsStatusMsg = "删除";
                break;
        }
        System.out.println("|  " + goods.getGoodsId() + "\t|"
                + goods.getGoodsName() + "\t|" + goods.getGoodsType() + "\t|"
                + goods.getGoodsNum() + "\t|" + goods.getGoodsPrice() + "\t|"
                + goodsStatusMsg + "\t|" + goods.getGoodsDiscount() + "\t|"
                + goods.getGoodsCreateTime() + "\t|" + goods.getGoodsUpdateTime());

        return ServiceResponse.success();
    }
}
