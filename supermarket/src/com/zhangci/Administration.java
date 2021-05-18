package com.zhangci;

import com.zhangci.consts.MarketConstants;
import com.zhangci.entity.Goods;
import com.zhangci.entity.GoodsType;
import com.zhangci.entity.Member;
import com.zhangci.service.*;
import com.zhangci.service.impl.*;
import com.zhangci.util.InputUtil;

import java.util.Objects;


/**
 * ClassName: Administration
 * <p>
 * Author: ZhangCi
 * Description: 管理类
 * Date: 2021/4/12 21:55
 * Version: 0.1
 * Since: JDK 1.8
 */
public class Administration {

    private InputUtil inputUtil;
    private GoodsService goodsService;
    private GoodsTypeService goodsTypeService;
    private MemberService memberService;
    private BuyGoodsService buyGoodsService;
    private SelectOrderService orderService;
    private RankService rankService;

    private Goods goods;
    private GoodsType goodsType;
    private Member member;


    //初始化
    private void init() {
        inputUtil = new InputUtil();

        goods = new Goods();
        goodsType = new GoodsType();
        member = new Member();

        goodsService = new GoodsServiceImpl();
        goodsTypeService = new GoodsTypeServiceImpl();
        memberService = new MemberServiceImpl();
        buyGoodsService = new BuyGoodsServiceImpl();
        orderService = new SelectOrderServiceImpl();
        rankService = new RankServiceImpl();

    }


    //系统首页
    private void mainMenu() {
        mainMenuMsg();

        //首页逻辑
        System.out.print("请选择菜单编号: ");
        int anInt = inputUtil.nextInt();

        //根据switch选择响应的菜单
        switch (anInt) {
            case 1:
                goodsTypeMgrModule();
                break;
            case 2:
                goodsMgrModule();
                break;
            case 3:
                memberMgrModule();
                break;
            case 4:
                buyMgrModule();
                break;
            case 5:
                orderSelectModule();
                break;
            case 6:
                rankStatisticsModule();
                break;
            default:
                System.out.println("输入有误，请确认后输入");
        }
        returnMenu();
    }

    //排行统计
    private void rankStatisticsModule() {
        //收银员身份验证
        isCashier();
        System.out.println("--------------------------------");
        System.out.println("当前页面---->" + MarketConstants.MAIN_RANK_STATISTICS);
        rankService.topTenAndTotalSalMonth();
    }

    //订单查询模块
    private void orderSelectModule() {
        //收银员身份验证
        isCashier();
        System.out.println("--------------------------------");
        System.out.println("当前页面---->" + MarketConstants.MAIN_ORDER_SELECT);
        orderService.selectMainMenu();
    }

    //购买管理模块
    private void buyMgrModule() {
        //收银员身份验证
        isCashier();
        System.out.println("--------------------------------");
        System.out.println("当前页面---->" + MarketConstants.MAIN_PURCHASE_MGR);

        buyGoodsService.goodsShopMenu();
    }

    //会员管理模块
    private void memberMgrModule() {
        //管理员身份验证
        isAdmin();
        System.out.println("--------------------------------");
        System.out.println("当前页面---->" + MarketConstants.MAIN_MEMBER_MGR);
        int menuId = submenuMemberMsg();

        switch (menuId) {
            case 1:
                //添加商品信息
                memberService.addMember(member);
                break;
            case 2:
                //修改商品信息
                memberService.updateMember(member);
                break;
            case 3:
                //查询全部商品信息
                memberService.selectMember();
                break;
            case 4:
                //删除商品信息
                memberService.deleteMemberById();
                break;
            case 5:
                memberService.mbBalanceAdd();
                break;
            default:
                System.out.println("输入有误");
                break;
        }
    }

    //商品管理模块
    private void goodsMgrModule() {
        //管理员身份验证
        isAdmin();

        System.out.println("--------------------------------");
        System.out.println("当前页面---->" + MarketConstants.MAIN_GOODS_MGR);
        int subMenuId = submenuGoodsMgrMsg();

        //根据菜单编号执行菜单功能
        switch (subMenuId) {
            case 1:
                //添加商品信息
                goodsService.addGoods(goods);
                break;
            case 2:
                //修改商品信息
                goodsService.updateGoods(goods);
                break;
            case 3:
                //查询全部商品信息
                goodsService.selectGoods();
                break;
            case 4:
                //删除商品信息
                goodsService.deleteGoodsById();
                break;
            default:
                System.out.println("输入有误");
                break;
        }
    }

    //商品类型管理模块
    private void goodsTypeMgrModule() {
        //管理员身份验证
        isAdmin();
        System.out.println("--------------------------------");
        System.out.println("当前页面---->" + MarketConstants.MAIN_GOODS_TYPE_MGR);
        //获得菜单编号
        int subMenuId = submenuGoodsTypeMsg();


        //根据菜单编号执行菜单功能
        switch (subMenuId) {
            case 1:
                //添加商品类型信息
                goodsTypeService.addGoodsType(goodsType);
                break;
            case 2:
                //修改商品类型信息
                goodsTypeService.updateGoodsType(goodsType);
                break;
            case 3:
                //查询全部商品类型信息
                goodsTypeService.selectGoodsType();
                break;
            case 4:
                //删除商品类型信息
                goodsTypeService.deleteGoodsTypeById();
                break;
            default:
                System.out.println("输入有误");
                break;
        }
    }

    //管理员身份验证
    private void isAdmin() {
        System.out.print("请输入用户名: ");
        String name = inputUtil.nextLine();
        System.out.print("请输入密  码: ");
        int pass = inputUtil.nextInt();

        //登录判断
        if (!Objects.equals(name, MarketConstants.ADMIN_ACCOUNT) || !Objects.equals(pass, MarketConstants.ADMIN_PASSWORD)) {
            showReturn();
        } else {
            System.out.println("登录成功，欢迎您 " + name);
            System.out.println();
        }
    }

    //收银员身份验证
    private void isCashier() {
        System.out.print("请输入用户名: ");
        String name = inputUtil.nextLine();
        System.out.print("请输入密  码: ");
        int pass = inputUtil.nextInt();

        //登录判断
        if (!Objects.equals(name, MarketConstants.CASHIER_ACCOUNT) || !Objects.equals(pass, MarketConstants.CASHIER_PASSWORD)) {
            showReturn();
        } else {
            //标识收银员登录
            System.out.println("登录成功，欢迎您 " + name);
            System.out.println();
        }
    }

    //退出或者返回菜单功能
    private void showReturn() {
        System.out.println("----------------------------------------------");
        System.out.println("用户名或者密码不正确，请进行如下操作：");
        System.out.println("      1. 退出");
        System.out.println("      2. 返回菜单");
        System.out.print("请选择菜单编号: ");
        int choose = inputUtil.nextInt();

        if (Objects.equals(choose, 1)) {
            System.out.println("系统正在退出！！！");
            System.exit(-1);
        } else if (Objects.equals(choose, 2)) {
            //返回菜单
            mainMenu();
        } else {
            System.out.println("输入无效！请重新输入");
            showReturn();
        }
    }

    //循环菜单
    private void returnMenu() {
        System.out.println("--------------------------------");
        System.out.print("是否退出系统(y/n): ");
        String answer = inputUtil.nextLine();
        if (Objects.equals(answer, "y")) {
            System.out.println("系统退出成功");
            System.exit(-1);
        } else {
            mainMenu();
        }
    }

    //主菜单信息
    private void mainMenuMsg() {
        System.out.println("----超市管(๑˃∀˂๑)理系统----");
        System.out.println("--------> 1. " + MarketConstants.MAIN_GOODS_TYPE_MGR);
        System.out.println("--------> 2. " + MarketConstants.MAIN_GOODS_MGR);
        System.out.println("--------> 3. " + MarketConstants.MAIN_MEMBER_MGR);
        System.out.println("--------> 4. " + MarketConstants.MAIN_PURCHASE_MGR);
        System.out.println("--------> 5. " + MarketConstants.MAIN_ORDER_SELECT);
        System.out.println("--------> 6. " + MarketConstants.MAIN_RANK_STATISTICS);
    }

    //子菜单：商品类型管理信息
    private int submenuGoodsTypeMsg() {
        System.out.println("        1. " + MarketConstants.ADD_GOODS_TYPE);
        System.out.println("        2. " + MarketConstants.UPDATE_GOODS_TYPE);
        System.out.println("        3. " + MarketConstants.SELECT_GOODS_TYPE);
        System.out.println("        4. " + MarketConstants.DELETE_GOODS_TYPE);
        System.out.print("请输入要操作的菜单编号: ");
        return inputUtil.nextInt();
    }

    //子菜单：商品管理信息
    private int submenuGoodsMgrMsg() {
        System.out.println("        1. " + MarketConstants.ADD_GOODS_MGR);
        System.out.println("        2. " + MarketConstants.UPDATE_GOODS_MGR);
        System.out.println("        3. " + MarketConstants.SELECT_GOODS_MGR);
        System.out.println("        4. " + MarketConstants.DELETE_GOODS_MGR);
        System.out.println();
        System.out.print("请输入要操作的菜单编号: ");
        return inputUtil.nextInt();
    }

    //子菜单：会员管理信息
    private int submenuMemberMsg() {
        System.out.println("        1. " + MarketConstants.ADD_MEMBER_MGR);
        System.out.println("        2. " + MarketConstants.UPDATE_MEMBER_MGR);
        System.out.println("        3. " + MarketConstants.SELECT_MEMBER_MGR);
        System.out.println("        4. " + MarketConstants.DELETE_MEMBER_MGR);
        System.out.println("        5. " + MarketConstants.MEMBER_BALANCE_RECHARGE);
        System.out.print("请输入要操作的菜单编号: ");
        return inputUtil.nextInt();
    }

    //通过无参构造来进行初始化
    public Administration() {
        init();
    }

    //启动
    public void start() {
        mainMenu();
    }
}
