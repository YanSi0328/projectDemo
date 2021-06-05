package com.finance.service.impl;

import com.finance.dao.ClientDao;
import com.finance.dao.impl.ClientDaoImpl;
import com.finance.entity.ClientInfo;
import com.finance.service.ClientInfoService;

import java.util.List;

/**
 * @author:Wang
 * @className: ClientInfoServiceImpl
 * @description:
 * @date: 2021/5/30 15:45
 * @version:0.1
 * @since:1.8
 */
public class ClientInfoServiceImpl implements ClientInfoService {
    @Override
    public List<ClientInfo> getClientByPage(int page, int pageSize, ClientInfo clientInfo) {
        ClientDao clientDao = new ClientDaoImpl();
        List<ClientInfo> list = null;
        try {
            list = clientDao.getClientByPage(page, pageSize, clientInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int getClientTotalPage(ClientInfo clientInfo) {
        ClientDao clientDao = new ClientDaoImpl();
        int totalPage = 0;
        try {
            totalPage = clientDao.getClientTotalPage(clientInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalPage;
    }
}
