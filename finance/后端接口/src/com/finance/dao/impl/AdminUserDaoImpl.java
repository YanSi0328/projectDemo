package com.finance.dao.impl;

import com.finance.dao.AdminUserDao;
import com.finance.entity.AdminUser;
import com.finance.util.DBHelper;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:HuJingJing
 * @className: AdminUserDaoImpl
 * @deacription:
 * @date: 2021/5/27 21:35
 * @version: 0.1
 * @since: 1.8
 */
public class AdminUserDaoImpl implements AdminUserDao {
    private PreparedStatement ps;
    private Connection conn;
    ResultSet rs=null;
    private  String sql;


    @Test
    public void test(){

        AdminUser adminUser = new AdminUser();
        adminUser.setAdminId(1);
        adminUser.setAdminName("胡晶晶");
        List<AdminUser> adminUsers1 = queryAdmin(1, 10, adminUser);
        System.out.println(adminUsers1);

//        List<AdminUser> adminUsers = queryAdmin(1, 10, new AdminUser(1,"胡晶晶"));
//        System.out.println(adminUsers);
    }

    @Override
    public Integer addAdmin(AdminUser adminUser) {
       conn = DBHelper.getConn();
       sql="INSERT INTO admin_user(admin_name,role_id,admin_status,admin_password,admin_img)VALUES(?,?,?,?,?)";
        try {
            ps=conn.prepareStatement(sql);
            //占位符
            ps.setString(1,adminUser.getAdminName());
            ps.setInt(2,adminUser.getRoleId());
            ps.setString(3,adminUser.getAdminStatus());
            ps.setString(4,adminUser.getAdminPassword());
            ps.setString(5,adminUser.getAdminImg());
           return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.CloseConn(conn,ps,rs);
        }
        return null;
    }

    @Override
    public List<AdminUser> queryAdmin(Integer page,Integer pageSize,AdminUser queryAd) {
        conn = DBHelper.getConn();
        Integer totalNum = 0;
        List<AdminUser>rslist=new ArrayList<AdminUser>();
        List  paramList = new ArrayList<>();
        sql="SELECT a.admin_id,a.admin_name,a.role_id,a.admin_status,a.admin_create_time,a.admin_update_time,a.menu_id,a.admin_password,a.admin_img FROM admin_user a ";
        //动态拼接
     Boolean iswhere=true;
        // 无key无值；有key无值
        // where 只能存在一个
     if (queryAd.getAdminId()!=null&&!"".equals(queryAd.getAdminId())&&queryAd.getAdminId()!=0){
         if (iswhere){
           sql+="where ";
           iswhere=false;
         }else {
             sql+=" and ";
         }
         sql+="a.admin_id=?";
         paramList.add(queryAd.getAdminId());

     }
     if (queryAd.getAdminName()!=null&&!"".equals(queryAd.getAdminName())){
         if (iswhere){
             sql+="where ";
             iswhere=false;
         }else{
             sql+=" and ";
         }
         sql+=" a.admin_name like ?";
        paramList.add("%"+queryAd.getAdminName()+"%");
     }
     sql+=" limit "+(page-1)*pageSize+","+pageSize;
        System.out.println(sql);
        try {
            ps=conn.prepareStatement(sql);
            for (int i = 0; i < paramList.size(); i++) {
                ps.setObject(i+1,paramList.get(i));//预编译
            }
            rs=ps.executeQuery();
            while (rs.next()){

                int adminId = rs.getInt("admin_id");
                String adminName = rs.getString("admin_name");
                int roleId = rs.getInt("role_id");
                String adminStatu =rs.getString("admin_status");
                Date adminCreateTime = new Date(rs.getTimestamp("admin_create_time").getTime());
                Date adminUpdateTime = new Date(rs.getTimestamp("admin_update_time").getTime());
                String menuId =rs.getString("menu_id");
                String adminPassword=rs.getString("admin_password");
                String adminImg =rs.getString("admin_img");
                rslist.add(new AdminUser(adminId,adminName,roleId ,adminStatu,adminCreateTime,adminUpdateTime,menuId,adminPassword,adminImg));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.CloseConn(conn,ps,rs);
        }
        return rslist;
    }




    @Override
    public Integer editAdmin(AdminUser adminUser) {
        conn = DBHelper.getConn();
        // a.admin_id,a.admin_name,a.role_id,a.admin_status,a.admin_create_time,a.admin_update_time,a.menu_id,a.admin_password,a.admin_img FROM admin_user a ";
        sql="UPDATE admin_user a set a.admin_name=?,a.role_id=?,a.admin_status=?,a.admin_password=?,a.admin_img=?,a.menu_id=? WHERE a.admin_id=?;";
        try {
            ps=conn.prepareStatement(sql);

            ps.setString(1,adminUser.getAdminName());
            ps.setInt(2,adminUser.getRoleId());
            ps.setString(3,adminUser.getAdminStatus());
            ps.setString(4,adminUser.getAdminPassword());
            ps.setString(5,adminUser.getAdminImg());
            ps.setString(6,adminUser.getMenuId());
            ps.setInt(7,adminUser.getAdminId());

                return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.CloseConn(conn,ps,rs);
        }
        return null;
    }

    @Override
    public Integer editAdminByName(AdminUser adminUser) {
        conn = DBHelper.getConn();

        sql="UPDATE admin_user a set a.admin_name=?,a.admin_password=? WHERE a.admin_id=?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,adminUser.getAdminName());
            ps.setString(2,adminUser.getAdminPassword());
            ps.setInt(3,adminUser.getAdminId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.CloseConn(conn,ps,rs);
        }
         return null;
    }

        @Override
    public Integer delAdmin(Integer id) {
            conn = DBHelper.getConn();
            sql="DELETE FROM admin_user  WHERE admin_id=?";
            try {
                ps=conn.prepareStatement(sql);
                ps.setInt(1,id);
                return ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DBHelper.CloseConn(conn,ps,rs);
            }
            return  null;

    }

    @Override
    public Integer getAdminNum(AdminUser queryAd) {
        conn = DBHelper.getConn();
        Integer totalNum=0;
        List rslist=new ArrayList();
        List  paramList = new ArrayList<>();
        sql="SELECT count(1) as total_num FROM admin_user ";
        //动态拼接
        Boolean iswhere=true;
        // 无key无值；有key无值
        // where 只能存在一个
        if (queryAd.getAdminId()!=null&&!"".equals(queryAd.getAdminId())&&queryAd.getAdminId()!=0){
            if (iswhere){
                sql+=" where ";
                iswhere=false;
            }else {
                sql+="and ";
            }
            sql+=" a.admin_id=?";
            paramList.add(queryAd.getAdminId());

        }
        if (queryAd.getAdminName()!=null&&!"".equals(queryAd.getAdminName())){
            if (iswhere){
                sql+=" where ";
                iswhere=false;
            }else{
                sql+="and ";
            }
            sql+=" admin_name like ?";
            paramList.add("%"+queryAd.getAdminName()+"%");
        }
        System.out.println(sql);
        try {
            ps = conn.prepareStatement(sql);
            for(int i = 0;i<paramList.size();i++){

                ps.setObject(i+1,paramList.get(i));
            }
            rs = ps.executeQuery();
            while(rs.next()){
                totalNum = rs.getInt("total_num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.CloseConn(conn,ps,rs);
        }
        return totalNum;
}

    @Override
    public Integer editPassword(Integer id, String pass) {
        conn = DBHelper.getConn();
        sql =  "update admin_user set admin_password=? where admin_id=?";
        Integer result = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, pass);
            ps.setInt(2, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.CloseConn(conn, ps, rs);
        }

        return result;
    }


    @Override
    public List<Integer> queryRoleid(Integer id) {
        String sql = " select role_id FROM admin_user WHERE admin_id=?";
        System.out.println(sql);
        Connection conn = DBHelper.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Integer> ls = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                int roleId = rs.getInt("role_id");
                ls.add(roleId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.CloseConn(conn, ps, rs);
        }


        return ls;

    }

    @Override
    public String getPassById(Integer id) {
        conn = DBHelper.getConn();
        sql = "SELECT admin_password FROM admin_user WHERE admin_id=?";
        String result = "";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getString("admin_password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.CloseConn(conn, ps, rs);
        }

        return result;

    }

    @Override
    public Integer editUserAuth(AdminUser editUser) {
        conn = DBHelper.getConn();
        Integer result = 0;
        String sql = "update admin_user set menu_id = ? where admin_id= ?";
        System.out.println(sql);
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,editUser.getMenuId());
            ps.setInt(2,editUser.getAdminId());
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.CloseConn(conn, ps, rs);
        }
        return result;
    }
}
