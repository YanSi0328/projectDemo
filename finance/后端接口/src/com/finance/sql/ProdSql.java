package com.finance.sql;

/**
 * ClassName: ProdSql
 * Author: ZhangCi
 *
 * @description: 有关产品操作的sql语句
 * @date: 2021/5/25 10:24
 * @version: 0.1
 * @since: 1.8
 */
public interface ProdSql {

    // 查询总的产品系列记录数
    String TOTAL_PS_RECORD = "SELECT COUNT(1) total FROM prod_Series";

    // 查询全部的商品系列信息：系列id,产品中名,产品英名,汇款信息
    String SEL_ALL_PS = "SELECT ps_id,p_Cname,p_Ename,remit_info FROM prod_Series";

    // 根据产品系列id查询指定信息
    String SEL_PS_ID = "SELECT p_canal,ps_name,p_Cname,p_Ename FROM prod_Series WHERE ps_id=?";

    // 新增产品系列信息：渠道、分类、中名、英名
    String ADD_PS_MSG = "INSERT INTO prod_Series(ps_id,p_canal,ps_name,p_Cname,p_Ename,create_time)VALUES(?,?,?,?,?,now())";

    // 根据 id 修改商品系列信息：渠道、分类、中名、英名
    String UP_PS_ID = "UPDATE prod_Series SET p_canal=?,ps_name=?,p_Cname=?,p_Ename=?,update_time=NOW() WHERE ps_id=?";

    // 根据 id 新增汇款信息：除去编号和日期共11个占位符
    String ADD_REMIT = "UPDATE prod_Series SET due_bankName=?,swift_code=?,due_bankArea=?,due_bankCity=?,benefit_name=?," +
            "benefit_account=?,benefit_address=?,remit_info=?,remit_remark=?,bank_code=?,cnaps_code=?,remit_time=NOW()" +
            "WHERE ps_id=?";


    // 子模块：产品基础信息
//    查询总记录数
    String SEL_PB_TOTAL = "SELECT COUNT(1) total FROM prod_basic";

    // 查询产品信息
    String SEL_PB_MSG = "SELECT p_id,p_name,p_category,p_series_category,admin_organ,open_date,fund_currency,review_status FROM prod_basic";

    // 查询产品名：为产品推荐新增中提供数据
    String SEL_PROD_NAME = "SELECT p_name FROM prod_basic";

    // 根据产品名称查询指定产品信息
    String SEL_BY_NAME = "SELECT p_category,p_series_category,p_name,admin_organ,annualized_yield,fund_currency," +
            "open_date,subscript_cycle,relative_manage_cost,subscript_rate,start_amount,charge_mode,redeem_cycle," +
            "init_redeem_amount,redeem_rate,redeem_lockup,p_reviewer FROM prod_basic WHERE p_name=?";

    // 新增产品信息，19个字段
    String ADD_PB_MSG = "INSERT INTO prod_basic(p_id,p_category,p_series_category,p_name,admin_organ,annualized_yield,fund_currency," +
            "open_date,subscript_cycle,relative_manage_cost,subscript_rate,start_amount,charge_mode,redeem_cycle,init_redeem_amount," +
            "redeem_rate,redeem_lockup,p_reviewer,p_creator,p_create_time)" +
            " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW());";

    // 根据产品名称修改产品信息，17个
    String UP_PB = "UPDATE prod_basic SET p_category=?,p_series_category=?,admin_organ=?,annualized_yield=?,fund_currency=?," +
            "open_date=?,subscript_cycle=?,relative_manage_cost=?,subscript_rate=?,start_amount=?,charge_mode=?,redeem_cycle=?," +
            "init_redeem_amount=?,redeem_rate=?,redeem_lockup=?,p_reviewer=?,p_update_time=now() WHERE p_name=?";

    // 净值的操作
    String SEL_PE = "SELECT COUNT(1) num FROM prod_equity WHERE p_name=?";
    // 新增
    String ADD_PE = "INSERT INTO prod_equity(p_name,average,base_date,growth_rate,create_time)VALUES(?,?,?,?,NOW())";
    // 修改
    String UP_PE = "UPDATE prod_equity SET average=?,base_date=?,growth_rate=?,update_time=NOW() WHERE p_name=?";


    // 子模块：产品推荐
    // 总记录数
    String SEL_PR_TOTAL = "SELECT COUNT(1) total FROM prod_recom";
    // 查询基本信息
    String SEL_PR_MSG = "SELECT id,p_category,p_name,degree,first_round,online_purchase,rank FROM prod_recom";

    // 添加推荐信息：想想产品的id、category怎么拿？产品id是在产品基础信息中查询出来的category怎么拿
    String ADD_PR = "INSERT INTO prod_recom(id,p_name,p_category,degree,online_purchase,invest_visible,first_round,reason,rank,create_time)VALUES(?,?,?,?,?,?,?,?,?,NOW())";

    // 修改推荐信息：产品名不能修改 那么产品名就是修改条件
    String UP_PR = "UPDATE prod_recom SET degree=?,online_purchase=?,invest_visible=?,first_round=?,reason=?,update_time=NOW() WHERE p_name=?";
    // 关联申购
    //  根据产品系列查询产品id和产品名称
    String SEL_ID_NAME = "SELECT id,p_name,related_pid FROM prod_recom WHERE p_category=?";

    // 根据产品名修改产品关联信息
    String UP_RELATE_PID = "UPDATE prod_recom SET related_pid=? WHERE id=?";


    // 子模块：产品审核
    // 总记录数
    // 查询基本信息，页面显示的五个信息，两个条件查询（prodName、review_status）
    String SEL_AUDIT = "SELECT p_name,p_series_category,p_creator,p_create_time,p_update_time FROM prod_basic";

    // 查询产品的审批人、审核状态
    String SEL_REVIEW = "SELECT review_status,p_reviewer FROM prod_basic WHERE p_name=?";
    // 查询待审核的产品信息
    String SEL_RA_PROD = "SELECT p_category,p_series_category,p_name,admin_organ,annualized_yield," +
            "fund_currency,open_date,subscript_cycle,relative_manage_cost,subscript_rate,start_amount," +
            "charge_mode,redeem_cycle,init_redeem_amount,redeem_rate,redeem_lockup,p_reviewer " +
            "FROM prod_basic WHERE prodId=?";
    // 根据 p_name 修改审核状态，状态值前台传递
    String UP_RA_STATUS = "UPDATE prod_basic SET review_status=?,review_time=now() WHERE p_name=?";

}
