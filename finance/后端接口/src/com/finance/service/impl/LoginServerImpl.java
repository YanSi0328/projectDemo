package com.finance.service.impl;

import com.finance.dao.LoginDao;
import com.finance.dao.MenuDao;
import com.finance.dao.impl.LoginDaoImpl;
import com.finance.dao.impl.MenuDaoImpl;
import com.finance.entity.AdminUser;
import com.finance.entity.Menu;
import com.finance.service.LoginServer;

import java.util.List;

/**
 * @author:HuJingJing
 * @className: LoginServerImpl
 * @deacription:
 * @date: 2021/5/28 16:47
 * @version: 0.1
 * @since: 1.8
 */
public class LoginServerImpl implements LoginServer {
//    @Override
//    public Integer login(String adminName, String adminPassword) throws NoSuchAlgorithmException {
////      adminPassword=MD5.encryption(adminPassword);
//        LoginDaoImpl loginDao = new LoginDaoImpl();
//        return  loginDao.(adminName,adminPassword);
//
//    }

    @Override
    public AdminUser login(AdminUser insertAd) {
        LoginDao ld = new LoginDaoImpl();
        return ld.Login(insertAd);
    }


    @Override
    public List<Menu> getAdminRole(AdminUser insertUser) {
        LoginDao ld = new LoginDaoImpl();
        //查询用户的权限信息 11001,11002,12001,12002,12003,13001,14001,14002,15001,16001,16002,19001,19002,11,12,13,14,15,16
        String adminRole = ld.getAdminRole(insertUser);
        MenuDao MenuDao = new MenuDaoImpl();
        //查一级菜单
//        List<Menu> lm1 = md.getParentMenu();
//        //查二级菜单
//        List<Menu> lm2 = md.getChildMenu();
        //查一级菜单
        List<Menu> lm1 = null;
        try {
            lm1 = MenuDao.getMenuByLevel(0, adminRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //查二级菜单
        List<Menu> lm2 = null;
        try {
            lm2 = MenuDao.getMenuByLevel(1, adminRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        }
        System.out.println(lm1 + "1111");
        System.out.println(lm2 + "2222");
        //遍历一级菜单 过程中遍历二级菜单
        //用二级菜单的pid 和一级菜单的mid匹配
        //如果匹配上 把当前遍历到的二级菜单 放入一级菜单的submenu中
        for (Menu m1 : lm1) {
            for (Menu m2 : lm2) {
                if (m2.getPId().equals(m1.getId())) {
                    m1.getSubmenu().add(m2);
                }
            }
        }
        return lm1;
    }
}




