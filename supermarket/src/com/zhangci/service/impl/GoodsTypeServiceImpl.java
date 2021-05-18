package com.zhangci.service.impl;


import com.zhangci.common.ServiceResponse;
import com.zhangci.dao.GoodsTypeDao;
import com.zhangci.dao.impl.GoodsTypeDaoImpl;
import com.zhangci.entity.GoodsType;
import com.zhangci.service.GoodsTypeService;
import com.zhangci.util.InputUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: GoodsTypeService
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/14 19:16
 * Version: 0.1
 * Since: JDK 1.8
 */
public class GoodsTypeServiceImpl implements GoodsTypeService {


    private static GoodsTypeDao goodsTypeDao;
    private static InputUtil inputUtil;

    static {
        goodsTypeDao = new GoodsTypeDaoImpl();
        inputUtil = new InputUtil();
    }

    @Override
    public ServiceResponse addGoodsType(GoodsType goodsType) {
        selectGoodsType();
        System.out.println("录入格式: 父ID(0-本目录1-以目录),名称,父类型(1-是 0-否),状态(1-正常，2-下架)");
        System.out.print("请输入商品类型各项信息：");
        String line = inputUtil.nextLine();
        String[] split = line.split(",");

        //测试信息：1,水果,1,1
        int pId = Integer.parseInt(split[0]);
        int orP = Integer.parseInt(split[2]);
        int status = Integer.parseInt(split[3]);

        //装入对象
        goodsType = new GoodsType(pId, split[1],
                orP, status);

        //将信息提供给dao层
        int record = goodsTypeDao.addGoodsType(goodsType);
        if (record != 1) {
            return ServiceResponse.error("新增商品信息失败");
        }
        System.out.println(goodsType.toString());
        return ServiceResponse.success("新增商品信息成功");
    }

    @Override
    public ServiceResponse updateGoodsType(GoodsType goodsType) {
        selectGoodsType();
        System.out.print("请录入要修改的商品类型id: ");
        int typeId = inputUtil.nextInt();
        Map<String, Object> goodsTypeMap = new HashMap<>(16);

        System.out.println("录入格式: 父ID(0-本目录 1-子目录),名称,父类型(1-是 0-否),状态(1-正常，2-下架)");
        System.out.print("请输入商品类型各项信息：");
        String line = inputUtil.nextLine(1);
        String[] split = line.split(",");

        goodsTypeMap.put("type_parent_id", split[0]);
        goodsTypeMap.put("type_name", split[1]);
        goodsTypeMap.put("type_or_parent", split[2]);
        goodsTypeMap.put("type_status", split[3]);

        int record = goodsTypeDao.updateGoodsType(goodsTypeMap, typeId);
        if (record != 1) {
            return ServiceResponse.error("新增商品信息失败");
        }
        System.out.println(goodsType.toString());
        return ServiceResponse.success(typeId + " 数据新增完毕");
    }

    @Override
    public void selectGoodsType() {
        List<GoodsType> goodsTypeList = goodsTypeDao.selectAllGoodsType();

        //接收常量
        String parent = null;
        String isParent;

        //输出商品类型表信息
        System.out.println("|——————————————————————————————————————————————————————————————————————————————————————|");
        System.out.println("|                               商   品   类  型  信   息   表                          |");
        System.out.println("|——————————————————————————————————————————————————————————————————————————————————————|");
        System.out.println("| 编号   父ID    名称  父类型  状态   创 建 时 间         更 新 时 间                      |");
        System.out.println("|——————————————————————————————————————————————————————————————————————————————————————|");

        //集合输出元素
        for (GoodsType goodsType : goodsTypeList) {
            if (goodsType.getTypeParentId() == 0) parent = "一级";
            if (goodsType.getTypeOrParent() == 1) isParent = "是";
            else isParent = "否";

            System.out.println("|  " + goodsType.getTypeId() + "\t|"
                    + parent + "\t|" + goodsType.getTypeName() + "\t|"
                    + isParent + "\t| " + goodsType.getTypeStatus() + "\t|"
                    + goodsType.getTypeCreateTime() + "\t|" + goodsType.getTypeUpdateTime() + "\t|");
        }
        System.out.println("|_______________________________________________________________________________________|");
        System.out.println();
        System.out.println("=======>>>  查询完毕~~~");
    }

    @Override
    public ServiceResponse deleteGoodsTypeById() {
        selectGoodsType();
        System.out.print("请录入要删除的商品类型id: ");
        int typeId = inputUtil.nextInt();

        int record = goodsTypeDao.deleteGoodsByIdType(typeId);
        if (record != 1) {
            return ServiceResponse.error("删除商品信息失败");
        }
        return ServiceResponse.success(typeId + " 删除商品信息成功");
    }
}
