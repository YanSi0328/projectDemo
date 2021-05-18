package com.zhangci.dao.impl;

import com.zhangci.dao.MemberDao;
import com.zhangci.entity.Member;
import com.zhangci.sql.AdminSql;
import com.zhangci.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MemberDaoImpl
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/14 22:13
 * Version: 0.1
 * Since: JDK 1.8
 */
public class MemberDaoImpl implements MemberDao {
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    private String sql;

    @Override
    public int addMember(Member member) {
        connection = DBHelper.getMysqlConnection(true);
        sql = AdminSql.INSERT_MEMBER;
        try {
            ps = connection.prepareStatement(sql);
            //匹配问号（问号和会员对象的连接）
            ps.setString(1, member.getMemberName());
            ps.setString(2, member.getMemberPassword());
            ps.setString(3, member.getMemberHeadImage());
            ps.setString(4, member.getMemberPhone());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, null);
        }
        return 0;
    }

    //测试：名称=张词,联系方式=99999999999
    @Override
    public int updateMember(Map<String, Object> params, int id) {
        connection = DBHelper.getMysqlConnection(true);
        StringBuilder upBuilder = new StringBuilder("UPDATE member set ");

        params.forEach((key, value) -> {
            //动态获取sql
            upBuilder.append(key);
            upBuilder.append("= '");
            upBuilder.append(value);
            upBuilder.append("',");
        });
        upBuilder.append("update_time = now() where id = ?");

        System.out.println("sql: " + upBuilder);
        try {
            ps = connection.prepareStatement(upBuilder.toString());
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, null);
        }
        return 0;
    }

    @Override
    public List<Member> selectMember() {
        connection = DBHelper.getMysqlConnection(true);
        sql = AdminSql.SELECT_MEMBER;

        List<Member> memberList = new ArrayList<>(10);

        try {
            ps = connection.prepareStatement(sql);
            //拿到返回的结果集
            rs = ps.executeQuery();

            while (rs.next()) {
                //结果集映射
                Member member = new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("head_image"),
                        rs.getString("phone"),
                        rs.getFloat("integral"),
                        rs.getDouble("balance"),
                        (LocalDateTime) rs.getObject("create_time"),
                        (LocalDateTime) rs.getObject("update_time"));
                memberList.add(member);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, rs);
        }
        return memberList;
    }

    @Override
    public int deleteMember(int id) {

        connection = DBHelper.getMysqlConnection(true);
        sql = AdminSql.DELETE_MEMBER;
        try {
            ps = connection.prepareStatement(sql);
            ps.setObject(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, rs);
        }

        System.out.println(id + " 的会员信息删除成功(Dao层)");
        return 0;
    }

    @Override
    public int updateMemberBalance(int money, int id) {
        connection = DBHelper.getMysqlConnection(true);
        sql = AdminSql.UPDATE_MEMBER_BALANCE;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, money);
            ps.setInt(2, id);
            //执行sql语句，返回受影响的记录条数
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Member selectMemberById(int id) {
        connection = DBHelper.getMysqlConnection(true);
        sql = AdminSql.SELECT_MEMBER_BY_ID;
        Member member = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            //结果集映射
            if (rs.next()) {
                member = new Member(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, rs);
        }
        return member;
    }
}
