package com.zhangci.etity;

import lombok.Data;

/**
 * ClassName: PageInfo
 * Author: ZhangCi
 *
 * @description: 页码信息
 * @date: 2021/5/14 21:31
 * @version: 0.1
 * @since: 1.8
 */
@Data
public class PageInfo {
    private Integer page;
    private Integer pageSize;
    private Integer totalPage;

    public PageInfo(Integer page, Integer pageSize, Integer totalPage) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
    }
}
