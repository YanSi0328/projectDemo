package com.finance.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * ClassName: ProdSeries
 * Author: ZhangCi
 *
 * @description: 实体类-产品系列
 * @date: 2021/5/24 22:54
 * @version: 0.1
 * @since: 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdSeries {

    private Integer psId;               // 产品系列ID
    private String psName;             // 产品系列名/产品分类：基金、保险(未使用)、证券(未使用
    private String pCname;             // 产品中文名
    private String pEname;             // 产品英文名
    private String remitInfo;          // 汇款信息概要（对应汇款信息表中的汇款用户、管理）
    // 新增
    private String pCanal;               // 产品渠道：香港资管、内地资管
    private LocalDateTime createTime;           // 创建时间
    private LocalDateTime updateTime;           // 修改时间

    // 汇款信息
    private String dueBankName;             // 收款银行名称
    private String swiftCode;               // SWIFT code银行国际代码
    private String dueBankArea;             // 收款银行地区
    private String dueBankCity;             // 收款银行城市
    private String benefitName;             // 收款人户名
    private String benefitAccount;          // 收款人账号
    private String benefitAddress;          // 收款人地址
    private String remitUser;                // 汇款用户(产品系列中的汇款信息概要 字段改用remitInfo)
    private String remitRemark;              // 汇款附言
    private String bankCode;                 // 银行代码
    private String cnapsCode;                // 人行系统支付行号
    private LocalDateTime remitTime;         // 汇款时间

    // 注意：不可以创建多个同类型的构造器
    // 查询：系列ID、中名、英名、汇款信息概略
    public ProdSeries(Integer psId, String pCname, String pEname, String remitInfo) {
        this.psId = psId;
        this.pCname = pCname;
        this.pEname = pEname;
        this.remitInfo = remitInfo;
    }

    // 新增：渠道、分类、中名、英名
    public ProdSeries(String pCanal, String psName, String pCname, String pEname) {
        this.pCanal = pCanal;
        this.psName = psName;
        this.pCname = pCname;
        this.pEname = pEname;
    }

    // 根据id修改信息
    public ProdSeries(Integer psId, String pCanal, String psName, String pCname, String pEname) {
        this.psId = psId;
        this.pCanal = pCanal;
        this.psName = psName;
        this.pCname = pCname;
        this.pEname = pEname;
    }

    // 汇款信息：
    public ProdSeries(Integer psId, String dueBankName, String swiftCode, String dueBankArea, String dueBankCity, String benefitName,
                      String benefitAccount, String benefitAddress, String remitInfo, String remitRemark,
                      String bankCode, String cnapsCode) {
        this.psId = psId;
        this.dueBankName = dueBankName;
        this.swiftCode = swiftCode;
        this.dueBankArea = dueBankArea;
        this.dueBankCity = dueBankCity;
        this.benefitName = benefitName;
        this.benefitAccount = benefitAccount;
        this.benefitAddress = benefitAddress;
        this.remitInfo = remitInfo;
        this.remitRemark = remitRemark;
        this.bankCode = bankCode;
        this.cnapsCode = cnapsCode;
    }
}
