package com.finance.service;

import com.finance.entity.EnterpriseInfo;

import java.util.List;
import java.util.Map;

/**
 * @author:Wang
 * @className: EnterpriseInfoService
 * @description:
 * @date: 2021/5/31 10:07
 * @version:0.1
 * @since:1.8
 */
public interface EnterpriseInfoService {
    /**
     * 查询企业信息集合
     *
     * @param ename    企业名称
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 查询企业的记录信息
     */
    List<EnterpriseInfo> getEnterpriseInfoByPage(String ename, int page, int pageSize);

    /**
     * 获取企业信息总记录数
     *
     * @param ename 企业名称
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
     * @return 企业id
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
