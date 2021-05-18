package com.zhangci.dao.impl;

import com.zhangci.dao.GoodsDao;
import com.zhangci.entity.Goods;
import com.zhangci.sql.AdminSql;
import com.zhangci.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * ClassName: GoodsDaoImpl
 * <p>
 * Author: ZhangCi
 * Description: 数据的持久化
 * Date: 2021/4/13 19:29
 * Version: 0.1
 * Since: JDK 1.8
 */
public class GoodsDaoImpl implements GoodsDao {
    private Connection connection;
    private Integer result;
    private PreparedStatement ps;
    private ResultSet rs;
    private String sql;

    /**
     * 新增商品信息
     *
     * @param goods 商品对象
     * @return
     */
    @Override
    public int addGoods(Goods goods) {
        //获得连接
        connection = DBHelper.getMysqlConnection(true);
        //准备sql语句
        sql = AdminSql.INSERT_GOODS;
        try {
            //预编译sql语句
            ps = connection.prepareStatement(sql);

            //通过匹配问号获得数据库
            ps.setObject(1, goods.getGoodsId());
            ps.setObject(2, goods.getGoodsName());
            ps.setObject(3, goods.getGoodsType());
            ps.setObject(4, goods.getGoodsNum());
            ps.setObject(5, goods.getGoodsPrice());
            ps.setObject(6, goods.getGoodsStatus());
            ps.setObject(7, goods.getGoodsDiscount());
            ps.setObject(8, goods.getGoodsCreateTime());
            ps.setObject(9, goods.getGoodsUpdateTime());

            System.out.println("=======>>>>商品信息新增成功");
            //执行sql语句
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, null);
        }
        return 0;
    }

    @Override
    public int updateGoods(Map<String, Object> params, int id) {
        connection = DBHelper.getMysqlConnection(true);
        //动态拼接sql语句
        StringBuilder builder = new StringBuilder("UPDATE goods set ");
        params.forEach((key, value) -> {
            builder.append(key);
            builder.append(" = '");
            builder.append(value);
            builder.append("',");
        });
        builder.append(" goods_update_time = now() where goods_id = ?");

        //最终sql语句
        System.out.println("sql: " + builder);
        try {
            ps = connection.prepareStatement(builder.toString());
            ps.setObject(1, id);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, null);
        }
        return result;
    }

    @Override
    public List<Goods> selectAllGoods() {
        connection = DBHelper.getMysqlConnection(true);
        //拿到sql语句
        sql = AdminSql.SELECT_ALL_GOODS;

        //商品集合存储商品
        List<Goods> goodsList = new ArrayList<>(10);

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            //拿到数据并将数据放入集合
            while (rs.next()) {
                //对结果集的处理放到了实体类当中，通过rs进行操作
                //每取得一个对象放入集合
                goodsList.add(new Goods(rs));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, rs);
        }
        return goodsList;
    }

    @Override
    public Goods selectGoodsById(int id) {
        connection = DBHelper.getMysqlConnection(true);
        sql = AdminSql.SELECT_GOODS_BY_ID;
        Goods goods = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            //得到结果集
            rs = ps.executeQuery();
            if (rs.next()) {
                goods = new Goods(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, rs);
        }
        return goods;
    }

    @Override
    public int deleteGoodsById(int id) {
        //获得连接
        connection = DBHelper.getMysqlConnection(true);
        //准备sql语句
        sql = AdminSql.DELETE_GOODS_BY_ID;

        try {
            ps = connection.prepareStatement(sql);
            //将问号通配符与传递过来的id进行匹配
            ps.setObject(1, id);
            //执行sql
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeResources(connection, ps, rs);
        }

        System.out.println(id + " 的商品删除成功(Dao层)");
        return result;
    }
}
