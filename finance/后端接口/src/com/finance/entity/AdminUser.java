package com.finance.entity;

import lombok.*;

import java.util.Date;

/**
 * @author:HuJingJing
 * @className: adminUser
 * @deacription: 人员管理（仅限管理员）
 * @date: 2021/5/27 20:37
 * @version: 0.1
 * @since: 1.8
 */

@Setter
@Getter
@NoArgsConstructor
@ToString
public class AdminUser {
    private Integer adminId;//后台管理员ID
    private String adminName;//后台管理员名称
    private Integer roleId;//角色id 1一级管理员 2二级管理员
    private String adminStatus;//后台管理员人员状态(0在职，1休假，2离职)
    private Date adminCreateTime;//创建时间(人员注册时间）
    private Date adminUpdateTime;//更新时间（人员最后登录时间）
    private String menuId;//菜单数组
    private String adminPassword;//后台管理员密码
    private String adminImg;//后台管理员头像路径
    private String newPass;//修改后的新密码
    //查询全部

    public AdminUser(Integer adminId, String adminName, Integer roleId, String adminStatus, Date adminCreateTime, Date adminUpdateTime, String menuId, String adminPassword, String adminImg) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.roleId = roleId;
        this.adminStatus = adminStatus;
        this.adminCreateTime = adminCreateTime;
        this.adminUpdateTime = adminUpdateTime;
        this.menuId = menuId;
        this.adminPassword = adminPassword;
        this.adminImg = adminImg;
    }

    //添加 名称、角色id、状态、密码、头像
    public AdminUser(String adminName, Integer roleId, String adminStatus, String adminPassword, String adminImg) {
        this.adminName = adminName;
        this.roleId = roleId;
        this.adminStatus = adminStatus;
        this.adminPassword = adminPassword;
        this.adminImg = adminImg;
    }

    //登录 名称、密码
    public AdminUser(String adminName, String adminPassword) {
        this.adminName = adminName;
        this.adminPassword = adminPassword;
    }

    public AdminUser(Integer adminId, String adminName, Date adminCreateTime, String adminPassword) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminCreateTime = adminCreateTime;
        this.adminPassword = adminPassword;
    }

    //添加 名称、角色id、状态、密码、头像
    public AdminUser(String adminName, Integer roleId, String adminStatus, String adminPassword, String adminImg, Integer adminId) {

        this.adminName = adminName;
        this.roleId = roleId;
        this.adminStatus = adminStatus;
        this.adminPassword = adminPassword;
        this.adminImg = adminImg;
        this.adminId = adminId;
    }

    public AdminUser(Integer adminId) {
        this.adminId = adminId;
    }

    public AdminUser(Integer adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;
    }
}
