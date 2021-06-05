package com.finance.service.impl;

import com.finance.dao.AdminUserDao;
import com.finance.dao.LoginDao;
import com.finance.dao.MenuDao;
import com.finance.dao.impl.AdminUserDaoImpl;
import com.finance.dao.impl.LoginDaoImpl;
import com.finance.dao.impl.MenuDaoImpl;
import com.finance.entity.AdminUser;
import com.finance.entity.Menu;
import com.finance.service.AdminUserService;

import java.util.List;

/**
 * @author:HuJingJing
 * @className: AdminUserServiceImpl
 * @deacription:
 * @date: 2021/5/28 19:27
 * @version: 0.1
 * @since: 1.8
 */
public class AdminUserServiceImpl implements AdminUserService {
    @Override
    public Integer addAdmin(AdminUser adminUser) {
        AdminUserDao ad = new AdminUserDaoImpl();
        return ad.addAdmin(adminUser);
    }

    @Override
    public List<AdminUser> queryAdmin(Integer page, Integer pageSize, AdminUser queryAd) {
        AdminUserDao ad = new AdminUserDaoImpl();
        return ad.queryAdmin(page, pageSize, queryAd);
    }

    @Override
    public Integer editAdmin(AdminUser adminUser) {
        AdminUserDao ad = new AdminUserDaoImpl();
        return ad.editAdmin(adminUser);
    }

    @Override
    public Integer editAdminByName(Integer id, String password) {
        return null;
    }

    @Override
    public Integer delAdmin(Integer id) {
        AdminUserDao ad = new AdminUserDaoImpl();
        return ad.delAdmin(id);
    }

    @Override
    public Integer getAdminNum(AdminUser queryAd) {
        AdminUserDao ad = new AdminUserDaoImpl();
        return ad.getAdminNum(queryAd);
    }

    @Override
    public Integer editPassword(Integer id, String pass) {
        AdminUserDao ad = new AdminUserDaoImpl();
        return ad.editPassword(id, pass);
    }


    @Override
    public String getPassById(Integer id) {
        AdminUserDao ad = new AdminUserDaoImpl();
        return ad.getPassById(id);

    }

    @Override
    public List<Menu> getAllAuth() {
        MenuDao md = new MenuDaoImpl();
        List<Menu> lm1 = null;
        try {
            lm1 = md.getMenuByLevel(0, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Menu> lm2 = null;
        try {
            lm2 = md.getMenuByLevel(1, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Menu m1 : lm1) {
            for (Menu m2 : lm2) {
                if (m2.getPId().equals(m1.getId())) {
                    boolean add = m1.getSubmenu().add(m2);
                    System.out.println(add + "用户菜单遍历了");
                }
            }
        }
        return lm1;
    }

    @Override
    public String getCurrentUserAuth(AdminUser queryUser) {
        LoginDao lg = new LoginDaoImpl();
        String adminRole = lg.getAdminRole(queryUser);

/*        String userAuth = "";
        if(adminRole!=null&&!"".equals(adminRole)){
            MenuDao md = new MenuDaoImpl();
            List<Menu> lm2 = md.getParentMenu();
            StringBuffer sb = new StringBuffer();
            lm2.forEach(menu -> {
                sb.append(menu.getId()).append(",");
            });
            userAuth = sb.deleteCharAt(sb.length() - 1).toString();
        }*/
        MenuDao md = new MenuDaoImpl();
        String userAuth = "";
        try {
            List<Menu> lm2 = md.getMenuByLevel(1, adminRole);
            StringBuffer sb = new StringBuffer();
            lm2.forEach(menu -> {
                sb.append(menu.getId()).append(",");
            });
            userAuth = sb.deleteCharAt(sb.length() - 1).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return userAuth;

    }

    @Override
    public Integer editUserAuth(AdminUser editUser) {
        AdminUserDao ad = new AdminUserDaoImpl();
        return ad.editUserAuth(editUser);
    }
}
