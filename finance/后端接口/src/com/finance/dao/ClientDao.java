package com.finance.dao;

import com.finance.entity.ClientInfo;


import java.util.List;

/**
 * @author:Wang
 * @className: ClientDao
 * @description:
 * @date: 2021/5/29 23:13
 * @version:0.1
 * @since:1.8
 */
public interface ClientDao {
    /**
     * @param page       页码
     * @param pageSize   页数
     * @param clientInfo 客户信息
     * @return 客户信息记录
     */
    List<ClientInfo> getClientByPage(int page, int pageSize, ClientInfo clientInfo) throws Exception;

    /**
     * @return 客户信息总记录数
     */
    int getClientTotalPage(ClientInfo clientInfo) throws Exception;
}
