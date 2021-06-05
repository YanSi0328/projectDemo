package com.finance.cons;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ClassName: ReturnWeb
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/20 21:55
 * @version: 0.1
 * @since: 1.8
 */
@Data
@AllArgsConstructor
public class ReturnWeb {
    private Integer code;
    private String returnMsg;
    private Object returnData;
    private PageInfo pageInfo;
    private Object extData;


    public ReturnWeb() {
    }
    public Object getExtData() {
        return extData;
    }
    // 只有操作记录
    public ReturnWeb(Integer code, String returnMsg) {
        this.code = code;
        this.returnMsg = returnMsg;
    }

    // 封装带有数据
    public ReturnWeb(Integer code, String returnMsg, Object returnData) {
        this.code = code;
        this.returnMsg = returnMsg;
        this.returnData = returnData;
    }

    public ReturnWeb(Integer code, String returnMsg, Object returnData, PageInfo pageInfo) {
        this.code = code;
        this.returnMsg = returnMsg;
        this.returnData = returnData;
        this.pageInfo = pageInfo;
    }
}
