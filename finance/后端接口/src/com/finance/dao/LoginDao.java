package com.finance.dao;

import com.finance.entity.AdminUser;


/**
 * @author:HuJingJing
 * @className: loginDao
 * @deacription:登录：一级管理/二级管理
 * @date: 2021/5/28 16:03
 * @version: 0.1
 * @since: 1.8
 */
public interface LoginDao {

    AdminUser Login(AdminUser insertAd);

    String getAdminRole(AdminUser insertAd);
}

