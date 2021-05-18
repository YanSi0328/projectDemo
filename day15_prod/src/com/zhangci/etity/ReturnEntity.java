package com.zhangci.etity;

import lombok.Data;

/**
 * ClassName: ReturnEntity
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/14 21:30
 * @version: 0.1
 * @since: 1.8
 */
@Data
public class ReturnEntity {
    private Integer code;
    private String returnMsg;
    private Object returnData;
    private PageInfo pageInfo;
}
