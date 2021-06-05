package com.finance.service;

import com.finance.entity.ClientInfo;

import java.util.List;

/**
 * @author:Wang
 * @className: ClientInfoService
 * @description:
 * @date: 2021/5/30 15:42
 * @version:0.1
 * @since:1.8
 */
public interface ClientInfoService {
    /**
     * @param page     页码
     * @param pageSize 页数
     * @return 客户信息记录
     */
    List<ClientInfo> getClientByPage(int page, int pageSize, ClientInfo clientInfo);

    /**
     * @return 客户信息总记录数
     */
    int getClientTotalPage(ClientInfo clientInfo);
}
