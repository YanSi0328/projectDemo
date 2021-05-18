package com.zhangci.dao.impl;

import com.zhangci.dao.GoodsTypeDao;
import com.zhangci.entity.GoodsType;
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
 * ClassName: GoodsTypeDaoImpl
 * <p>
 * Author: ZhangCi
 * Description: 商品类型的dao处理
 * Date: 2021/4/14 19:18
 * Version: 0.1
 * Since: JDK 1.8
 */
public class GoodsTypeDaoImpl implements GoodsTypeDao {
    private Connection connection;
    private Integer result;
    private PreparedStatement ps;
    private ResultSet rs;
    private String sql;


    @Override
    public int addGoodsType(GoodsType goodsType) {
        connection = DBHelper.getMysqlConnection(true);
        sql = AdminSql.INSERT_GOODSTYPE;
        try {
            ps = connection.prepareStatement(sql);
            //问号匹配
            ps.setObject(1, goodsType.getTypeId());
            ps.setObject(2, goodsType.getTypeParentId());
            ps.setObject(3, goodsType.getTypeName());
            ps.setObject(4, goodsType.getTypeOrParent());
            ps.setObject(5, goodsType.getTypeStatus());
            ps.setObject(6, goodsType.getTypeUpdateTime());

            System.out.println("Dao 层已经映射");
            System.out.println("=======>>>>商品类型信息新增成功");
            //执行sql并返回受影响的记录数
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, null);
        }
        return 0;
    }

    //一种思想：将此id对应的信息进行删除，然后插入指定id的信息
    @Override
    public int updateGoodsType(Map<String, Object> params, int id) {
        connection = DBHelper.getMysqlConnection(true);
        StringBuilder builder = new StringBuilder("UPDATE goodstype set ");
        //字段拼接
        params.forEach((key, value) -> {
            builder.append(key);
            builder.append(" = '");
            builder.append(value);
            builder.append("', ");
        });
        //末尾字段
        builder.append("type_update_time = now() where type_id = ?");

        try {
            ps = connection.prepareStatement(builder.toString());
            ps.setObject(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, rs);
        }
        return result;
    }

    @Override
    public List<GoodsType> selectAllGoodsType() {
        //获得数据库连接，并保持开、
        connection = DBHelper.getMysqlConnection(true);
        //sql语句
        sql = AdminSql.SELECT_ALL_GOODSTYPE;
        //商品类型集合
        List<GoodsType> goodsTypeList = new ArrayList<>(10);

        try {
            ps = connection.prepareStatement(sql);
            //拿到结果集
            rs = ps.executeQuery();
            //遍历结果集
            while (rs.next()) {
                GoodsType goodsType = new GoodsType(
                        rs.getInt("id"),
                        rs.getInt("pid"),
                        rs.getString("name"),
                        rs.getInt("parent"),
                        rs.getInt("status"),
                        (LocalDateTime) rs.getObject("create_time"),
                        (LocalDateTime) rs.getObject("update_time"));
                //将对象放入集合
                goodsTypeList.add(goodsType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, rs);
        }
        return goodsTypeList;
    }

    @Override
    public int deleteGoodsByIdType(int id) {
        connection = DBHelper.getMysqlConnection(true);
        sql = AdminSql.DELETE_GOODSTYPE_BY_ID;

        try {
            ps = connection.prepareStatement(sql);
            //匹配问号
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, rs);
        }
        System.out.println("dao:删除商品类型成功");
        return 0;
    }
}
