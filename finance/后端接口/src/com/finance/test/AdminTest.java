package com.finance.test;

import com.finance.dao.AdminUserDao;
import com.finance.dao.impl.AdminUserDaoImpl;
import com.finance.entity.AdminUser;
import com.finance.service.AdminUserService;
import com.finance.service.LoginServer;
import com.finance.service.impl.AdminUserServiceImpl;
import com.finance.service.impl.LoginServerImpl;
import lombok.ToString;
import org.junit.Test;


/**
 * @author:HuJingJing
 * @className: AdminTest+loginTest
 * @deacription:
 * @date: 2021/5/28 9:45
 * @version: 0.1
 * @since: 1.8
 */
@ToString
public class AdminTest {
    AdminUserService adminUserService = new AdminUserServiceImpl();
    AdminUserDao adminUserDao = new AdminUserDaoImpl();
    LoginServer loginServer = new LoginServerImpl();

    @Test
    public void testAddAdminMsg() {
        System.out.println(adminUserService.addAdmin(new AdminUser("王中文", 2, "1", "321456", "http://localhost:8080/picture/5.jpg")));
    }

    @Test
    public void testeditAdminMsg() {
        System.out.println(adminUserService.editAdmin(new AdminUser("胡图图", 2, "2", "321456", "http://localhost:8080/picture/5.jpg", 4)));
    }

    @Test
    public void testqueryAdminMsg() {
        System.out.println(adminUserService.queryAdmin(1, 2, new AdminUser()));
    }

    @Test
    public void testloginMsg() {

//            System.out.println(loginServer.login("胡图图", "321456")));
        System.out.println(loginServer.login(new AdminUser("胡图图", "321456")));


    }

    @Test
    public void testAdminTotal() {
        System.out.println(adminUserService.getAdminNum(new AdminUser()));
    }

    @Test
    public void testAdminPassById() {
        System.out.println(adminUserService.getPassById(1));
    }

    @Test
    public void testAdmineditPass() {
        System.out.println(adminUserService.editPassword(5, "123456"));
    }

    @Test
    public void testloginPass() {
        System.out.println(loginServer.getAdminRole(new AdminUser(1)));
    }
}

