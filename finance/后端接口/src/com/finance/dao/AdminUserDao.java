package com.finance.dao;

import com.finance.entity.AdminUser;

import java.util.List;

/**
 * @author:HuJingJing
 * @className: adminUserDao
 * @deacription: 管理员dao包
 * @date: 2021/5/27 21:03
 * @version: 0.1
 * @since: 1.8
 */
public interface AdminUserDao {
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

    //    /**
//     * 根据id修改密码
//     * @param name 用户id
//     * @param password 用户密码
//     * @return
//     */
    Integer editAdminByName(AdminUser adminUser);

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
     * 用户修改密码
     *
     * @param id 根据id修改
     * @return
     */
    Integer editPassword(Integer id, String pass);

    /**
     * @param id 根据id查询密码
     * @return
     */
    String getPassById(Integer id);

    /**
     * 根据角色id查询
     *
     * @param uid
     * @return
     */
    List<Integer> queryRoleid(Integer uid);

    /**
     * 修改权限
     *
     * @param editUser
     * @return
     */
    Integer editUserAuth(AdminUser editUser);
}
