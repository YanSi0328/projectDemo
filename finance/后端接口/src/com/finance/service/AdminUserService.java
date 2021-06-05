package com.finance.service;

import com.finance.entity.AdminUser;
import com.finance.entity.Menu;

import java.util.List;

/**
 * @author:HuJingJing
 * @className: AdminUserService
 * @deacription:
 * @date: 2021/5/28 13:43
 * @version: 0.1
 * @since: 1.8
 */
public interface AdminUserService {
    /**
     * 管理员新增内部员工
     *
     * @param adminUser 管理员权限
     * @return 新增操作后的记录数
     */
    Integer addAdmin(AdminUser adminUser);

    /**
     * 查询全部信息
     *
     * @param page     当前页码
     * @param pageSize 每页页码记录数
     * @param queryAd  查询参数
     * @return 查询操作后的记录数
     */
    List<AdminUser> queryAdmin(Integer page, Integer pageSize, AdminUser queryAd);


    /**
     * 修改人员信息
     *
     * @param adminUser
     * @return 修改操作后的记录数
     */
    Integer editAdmin(AdminUser adminUser);

    /**
     * 根据id修改密码
     *
     * @param id       用户id
     * @param password 用户密码
     * @return
     */
    Integer editAdminByName(Integer id, String password);

    /**
     * 根据id删除人员信息
     *
     * @param id
     * @return 删除操作后的记录数
     */
    Integer delAdmin(Integer id);

    /**
     * 总记录数
     *
     * @param queryAd
     * @return 操作后的记录数
     */
    Integer getAdminNum(AdminUser queryAd);

    /**
     * 修改密码
     *
     * @param id   根据id修改密码
     * @param pass
     * @return 操作后的记录数
     */
    Integer editPassword(Integer id, String pass);

    /**
     * @param id 根据id查询密码
     * @return 操作后的记录数
     */
    String getPassById(Integer id);


    //权限
    List<Menu> getAllAuth();

    String getCurrentUserAuth(AdminUser queryUser);

    Integer editUserAuth(AdminUser editUser);
}
