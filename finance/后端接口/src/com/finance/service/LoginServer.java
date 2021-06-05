package com.finance.service;

import com.finance.entity.AdminUser;
import com.finance.entity.Menu;

import java.util.List;

/**
 * @author:HuJingJing
 * @className: LoginServer
 * @deacription:
 * @date: 2021/5/28 16:46
 * @version: 0.1
 * @since: 1.8
 */
public interface LoginServer {
    //    Integer login(String adminName,String adminPassword) throws NoSuchAlgorithmException;
    AdminUser login(AdminUser insertAd);

    List<Menu> getAdminRole(AdminUser insertUser);
}
