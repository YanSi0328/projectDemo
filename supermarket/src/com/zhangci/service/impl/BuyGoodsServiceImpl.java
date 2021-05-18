package com.zhangci.service.impl;


import com.zhangci.common.ServiceResponse;
import com.zhangci.dao.GoodsDao;
import com.zhangci.dao.MemberDao;
import com.zhangci.dao.OrderDao;
import com.zhangci.dao.impl.GoodsDaoImpl;
import com.zhangci.dao.impl.MemberDaoImpl;
import com.zhangci.dao.impl.OrderDaoImpl;
import com.zhangci.entity.*;
import com.zhangci.service.BuyGoodsService;
import com.zhangci.service.GoodsService;
import com.zhangci.util.InputUtil;


import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName: BuyGoodsServiceImpl
 * <p>
 * Author: ZhangCi
 * Description: 业务逻辑层
 * Date: 2021/4/15 12:11
 * Version: 0.1
 * Since: JDK 1.8
 */
public class BuyGoodsServiceImpl implements BuyGoodsService {

    //饿汉模式
    private static GoodsDao goodsDao;
    private static MemberDao memberDao;
    private static GoodsService goodsService;
    private static OrderDao orderDao;
    private static InputUtil inputUtil;
    private static Map<Integer, GoodsShopping> shoppingMap;
    private static Collection<GoodsShopping> values;
    private static List<Integer> gIdList;
    private static Goods goods;
    private static Float money;
    private static Integer buyNum;

    private static int memberId;
    private static int orderId;

    static {
        goodsDao = new GoodsDaoImpl();
        memberDao = new MemberDaoImpl();
        goodsService = new GoodsServiceImpl();
        orderDao = new OrderDaoImpl();
        inputUtil = new InputUtil();
        shoppingMap = new HashMap<>(16); // 使用集合存储已经购买的商品信息
        values = shoppingMap.values(); //购物车商品
        gIdList = new ArrayList<>(10); //商品信息


        money = 0f;
        buyNum = 0;
        memberId = 0;
        orderId = 0;
    }


    //购物车功能
    @Override
    public void goodsShopMenu() {
        goodsService.selectGoods();

        String answer;
        do {
            System.out.println("购物车管理");
            System.out.println();
            System.out.println("        1. 向购物车添加商品");
            System.out.println("        2. 修改购物车商品数量");
            System.out.println("        3. 删除购物车中的商品");
            System.out.println("        4. 展示购物车商品列表");
            System.out.print("请录入菜单编号：");
            int menuId = inputUtil.nextInt();

            switch (menuId) {
                case 1:
                    // 添加商品
                    addGoods();
                    break;
                case 2:
                    // 修改商品数量
                    updateGoodsNum();
                    break;
                case 3:
                    // 删除购物车中的商品
                    deleteShopping();
                    break;
                case 4:
                    // 展示购物车商品列表
                    showShopCar();
                    break;
                default:
                    System.out.println("菜单功能暂未开发。。。。。。。。");
                    break;
            }
            System.out.println("是否退出购物车(y/n)");
            answer = inputUtil.nextLine();

        } while (Objects.equals(answer, "n"));

        System.out.println("  购物车退出成功  ");


        //持久化数据
        //进行支付
        int result = paymentWay(money);
        if (result == 0) {
            //支付未成功
            System.out.println("支付失败，请重新支付");
            paymentWay(money);
        } else {
            //将订单信息初始化到订单信息表，只有使用了会员卡支付才会有会员号
            OrderMsg orderMsg = new OrderMsg(memberId, money, result);
            orderDao.addOrderMsg(orderMsg);
        }

        //根据商品id放入订单详情表
        gIdList.forEach(System.out::println);
        for (Integer aGIdList : gIdList) {
            OrderDetails orderDetails = new OrderDetails(orderId, aGIdList, buyNum);
            orderDao.addOrderDetails(orderDetails);
        }
    }

    //添加商品信息
    private void addGoods() {
        shoppingMap = loopStoreGoods();
        values = shoppingMap.values();
        for (GoodsShopping value : values) {
            money = money + value.getTotalMoney();
            buyNum = buyNum + value.getBuyNum();

            //每拿到一个商品编号，就将商品编号放入集合
            gIdList.add(value.getGoods().getGoodsId());
        }
        System.out.println("money： " + money);
    }

    // 修改商品数量，不能覆盖之前的数据，所以只可以对数据进行操作，不能返回数据
    @Override
    public ServiceResponse updateGoodsNum() {
        if (values.isEmpty()) {
            return ServiceResponse.error("请先向购物车添加商品信息");
        }
        System.out.print("请录入要修改的商品编号");
        int gId = inputUtil.nextInt();
        System.out.print("请录入要修改的商品数量");
        int gNum = inputUtil.nextInt();

        //拿到商品里面的单价、折扣
        goods = goodsDao.selectGoodsById(gId);
        float gDis = goods.getGoodsDiscount();
        float gPri = goods.getGoodsPrice();

        //计算总金额
        money = gDis * gPri * gNum;

        //找到指定编号的商品，并修改指定元素的值，最后将元素收集
        //执行逻辑是先走右边，故可以将对values操作后的结果返回给values
        //修改的是购物车里面的信息
        values = values.stream()
                .filter(selId -> selId.getGoods().getGoodsId() == gId)
                .peek(upNum -> upNum.setBuyNum(gNum))
                .peek(upMon -> upMon.setTotalMoney(money))
                .collect(Collectors.toList());

        System.out.println("修改后的购物车信息   ----> ");
        showShopCar();
        return ServiceResponse.success();
    }

    //删除购物车信息
    @Override
    public ServiceResponse deleteShopping() {
        if (values.isEmpty()) {
            return ServiceResponse.error("请先向购物车添加商品信息");
        }
        System.out.println("请录入要删除的购物车记录编号: ");
        int anInt = inputUtil.nextInt();

        //过滤掉不符合条件的购物车记录，现在的购物车是 values
        values = values.stream().filter(val -> val.getGoods().getGoodsId() != anInt).collect(Collectors.toList());

        System.out.println("删除后: ");
        shoppingMap.forEach((key, value) -> System.out.println(key + "->" + value));

        return ServiceResponse.success();
    }

    /**
     * 展示购物车信息
     *
     * @return 查询是否成功
     */
    @Override
    public ServiceResponse showShopCar() {
        if (values.isEmpty()) {
            return ServiceResponse.error("请先向购物车添加商品信息");
        }
        System.out.println("--------->购物车商品列表");
        System.out.println();
        System.out.println("账单编号\t商品名称\t购买数量\t总金额");
        for (GoodsShopping value : values) {
            System.out.println("  " + value.getGoods().getGoodsId() + "\t"
                    + value.getGoods().getGoodsName() + "\t"
                    + value.getBuyNum() + "\t"
                    + value.getTotalMoney());
        }

        return ServiceResponse.success();
    }

    /**
     * 订单结算
     *
     * @param money 钱
     * @return 是否支付成功
     */
    @Override
    public int paymentWay(Float money) {
        // 确认购买后：提示“是否用会员卡支付”？
        // 如果是，输入会员卡号，并扣除金额。
        // 如果不是，则提示现金支付。
        //金额不为0时进行支付
        if (money == 0) {
            System.out.println("您此次未购买任何商品，无需进行支付");
            return 0;
        }
        System.out.println("是否使用会员卡支付(是/不是): ");
        String answer = inputUtil.nextLine();

        if (Objects.equals(answer, "是")) {

            System.out.println("请输入会员卡号: ");
            memberId = inputUtil.nextInt();
            //根据会员卡号进行查询会员表
            Member member = memberDao.selectMemberById(memberId);
            //对会员卡余额进行判断
            if (money > member.getMemberBalance()) {
                System.out.println("会员卡余额不足，请进行充值后付款");
                return 0;
            }
            //修改会员信息表金额记录
            Map<String, Object> sqlMap = new HashMap<>(16);
            sqlMap.put("balance", member.getMemberBalance() - money);

            memberDao.updateMember(sqlMap, memberId);
            System.out.println("会员 " + member.getMemberName() + " 支付成功，扣除余额 " + money);
            System.out.println("修改金额后的 member: " + member.toString());
            return 1;

        } else if (Objects.equals(answer, "不是")) {
            System.out.println("请输入现金: ");
            int pay = inputUtil.nextInt();

            //判断现金是否与消费的金额一致
            float result = pay - money;
            if (result < 0) {
                System.out.println("现金不足");
                return 0;
            }
            System.out.println("支付成功，找零 " + result + " 元");
            return 2;
        }
        //都不满足条件
        System.out.println("抱歉，暂时不支持其他的支付方式");
        return 0;
    }

    //循环存储商品信息
    @Override
    public Map<Integer, GoodsShopping> loopStoreGoods() {

        //更新商品数量的sql字段
        Map<String, Object> sqlMap = new HashMap<>(16);

        //计算金额
        Float totalMoney;

        String answer;
        do {
            //查询指定id的商品信息
            System.out.print("请录入要购买的商品id: ");
            Integer goodsId = inputUtil.nextInt();
            //每次都是去数据库中查找
            goods = goodsDao.selectGoodsById(goodsId);

            //显示此商品的详情信息
            System.out.println("| 编号   |名称    |类型    |数量 |单价(元) |状态   |折扣(%)    |创 建 时 间           |更 新 时 间");
            //查看商品信息
            goodsService.showGoods(goods);

            System.out.println("请录入要购买的商品数量: ");
            int buyNum = inputUtil.nextInt();

            //判断商品是否可以购买
            if (goods.getGoodsStatus() != 1) {
                System.out.println("商品暂时未上架，请等待上架");
                break;
            }
            //判断商品数量是否够
            if (!(buyNum <= goods.getGoodsNum())) {
                System.out.println("商品数量不够，请联系管理员上货");
                break;
            }

            //判断是否购买过
            GoodsShopping shopping = shoppingMap.get(goodsId);
            if (shopping != null) {
                shopping.setBuyNum(shopping.getBuyNum() + buyNum);
                //计算金额
                totalMoney = shopping.getBuyNum() * goods.getGoodsPrice() * goods.getGoodsDiscount();
                //未购买过直接将购买的商品信息放入购物车
                shoppingMap.put(goodsId, new GoodsShopping(goods, shopping.getBuyNum(), totalMoney));
            } else {
                //计算金额
                totalMoney = buyNum * goods.getGoodsPrice() * goods.getGoodsDiscount();
                //未购买过直接将购买的商品信息放入购物车
                shoppingMap.put(goodsId, new GoodsShopping(goods, buyNum, totalMoney));
            }


            //小bug：当没有退出此次购买，也就是说，实际上没有购买，但是数据库其实已经更新了
            //购买后 goods 里面的商品数量要减少，对 goods 表的操作
            sqlMap.put("goods_num", goods.getGoodsNum() - buyNum);

            System.out.println("此时的sql商品数量:  " + (goods.getGoodsNum() - buyNum));
            goodsDao.updateGoods(sqlMap, goodsId);

            System.out.println();
            System.out.print("是否继续购买(y/n): ");
            answer = inputUtil.nextLine(1);
        } while ("y".equals(answer));

        System.out.println(goods.getGoodsName() + "    加入购物车成功   ");
        shoppingMap.forEach((key, value) -> System.out.println(key + "->" + value));

        //将购买的商品信息返回出去
        return shoppingMap;
    }
}
