package com.zhangci.sql;

/**
 * ClassName: AdminSql
 * <p>
 * Author: ZhangCi
 * Description: 商品的sql语句信息
 * Date: 2021/4/14 10:23
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface AdminSql {


    //新增商品的全部信息
    String INSERT_GOODS = "INSERT INTO goods (id,name,type,num,price,status,discount,create_time,update_time) VALUES (?,?,?,?,?,?,?,?,?)";

    //根据查询全部的商品信息
    String SELECT_ALL_GOODS = "SELECT * FROM goods";

    //根据id查询单个商品的信息
    String SELECT_GOODS_BY_ID = "SELECT * FROM goods WHERE id = ?";

    //根据id删除商品信息
    String DELETE_GOODS_BY_ID = "DELETE FROM goods WHERE id = ?";



    //新增商品类型的全部信息
    String INSERT_GOODSTYPE = "INSERT INTO goodsType(id,pid,name,parent,status,create_time,update_time) VALUES(?,?,?,?,?,now(),?)";
    //根据查询全部的商品类型信息
    String SELECT_ALL_GOODSTYPE = "SELECT * FROM goodsType";

    //根据id删除商品类型信息
    String DELETE_GOODSTYPE_BY_ID = "DELETE FROM goodsType WHERE id = ?";




    //添加会员信息
    String INSERT_MEMBER = "INSERT INTO member(name,password,head_image,phone,create_time,update_time) VALUES(?,?,?,?,now(),now())";

    //查询会员信息
    String SELECT_MEMBER = "SELECT * FROM member";

    //根据 id 查询会员信息
    String SELECT_MEMBER_BY_ID = "SELECT * FROM member WHERE id =?";

    //删除会员信息
    String DELETE_MEMBER = "DELETE FROM member WHERE id = ?";

    //充值会员余额
    String UPDATE_MEMBER_BALANCE = "UPDATE member SET balance = (balance + ?) WHERE id = ?";

}

