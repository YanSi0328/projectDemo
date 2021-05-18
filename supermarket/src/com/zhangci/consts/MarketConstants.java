package com.zhangci.consts;

/**
 * ClassName: MarketConstants
 * <p>
 * Author: ZhangCi
 * Description: 存放超市管理系统的常量
 * Date: 2021/4/12 22:02
 * Version: 0.1
 * Since: JDK 1.8
 */
public class MarketConstants {

    //时间格式化样式
    public static final String TIME_FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";

    //主菜单常量
    public static final String MAIN_GOODS_TYPE_MGR = "商品类型管理";
    public static final String MAIN_GOODS_MGR = "商 品 管 理";
    public static final String MAIN_MEMBER_MGR = "会 员 管 理";
    public static final String MAIN_PURCHASE_MGR = "购 买 管 理";
    public static final String MAIN_ORDER_SELECT = "订 单 查 询";
    public static final String MAIN_RANK_STATISTICS = "排 行 统 计";

    //子菜单常量：显示商品类型功能菜单
    public static final String ADD_GOODS_TYPE = "添加商品类型信息";
    public static final String UPDATE_GOODS_TYPE = "修改商品类型信息";
    public static final String SELECT_GOODS_TYPE = "查询商品类型信息";
    public static final String DELETE_GOODS_TYPE = "删除商品类型信息";

    //子菜单常量：显示商品管理功能菜单
    public static final String ADD_GOODS_MGR = "添加商品信息";
    public static final String UPDATE_GOODS_MGR = "修改商品信息";
    public static final String SELECT_GOODS_MGR = "查询商品信息";
    public static final String DELETE_GOODS_MGR = "删除商品信息";

    //子菜单常量：显示会员管理功能菜单
    public static final String ADD_MEMBER_MGR = "添加会员信息";
    public static final String UPDATE_MEMBER_MGR = "修改会员信息";
    public static final String SELECT_MEMBER_MGR = "查询会员信息";
    public static final String DELETE_MEMBER_MGR = "删除会员信息";
    public static final String MEMBER_BALANCE_RECHARGE = "会员余额充值";


    //使用静态常量设置管理员账户密码/收银员账户密码
    public static final String ADMIN_ACCOUNT = "admin";
    public static final Integer ADMIN_PASSWORD = 123;
    public static final String CASHIER_ACCOUNT = "cash";
    public static final Integer CASHIER_PASSWORD = 12;

    //父级目录的地址
    public static final String PARENT_DIRECTORY = "upload/user/";

    //制定盐值
    public static final String MD5SALT = "ZHANDCI";
}
