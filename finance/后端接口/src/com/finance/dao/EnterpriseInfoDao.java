package com.finance.dao;

import com.finance.entity.EnterpriseInfo;

import java.util.List;
import java.util.Map;

/**
 * @author:Wang
 * @className: EnterpriseInfoDao
 * @description:
 * @date: 2021/5/31 9:44
 * @version:0.1
 * @since:1.8
 */
public interface EnterpriseInfoDao {
    /**
     * 查询多个企业信息
     *
     * @param ename    企业名称
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 查询企业的记录信息
     */
    List<EnterpriseInfo> getEnterpriseInfoByPage(String ename, int page, int pageSize);

    /**
     * 获取总记录数
     * ename 企业名称
     *
     * @return 查询企业的记录数
     */
    Integer getEnterpriseTotalPage(String ename);

    /**
     * 添加企业信息
     *
     * @param paramMap 参数集合
     * @return 受影响的记录数
     */
    Integer addEnterpriseInfo(Map<String, Object> paramMap);

    /**
     * 查询单个企业信息
     *
     * @param name 企业名称
     * @param code 股票代码
     * @return
     */
    EnterpriseInfo getEnterpriseByParms(String name, String code);

    /**
     * 修改企业信息
     *
     * @param paramMap 参数集合
     * @return 受影响的记录数
     */
    Integer editEnterpriseInfo(Map<String, Object> paramMap);

    /**
     * @return 企业类型集合
     */
    List<String> getEnterpriseType();

}
