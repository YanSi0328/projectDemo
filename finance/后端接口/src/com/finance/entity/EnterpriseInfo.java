package com.finance.entity;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author:Wang
 * @className: EnterpriseInfo
 * @description: 独角兽企业信息类
 * @date: 2021/5/29 22:43
 * @version:0.1
 * @since:1.8
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnterpriseInfo {
    private Integer enterpriseId;// 独角兽企业管理顺序(独角兽企业编号)
    private String enterpriseName;//企业名称
    private String enterpriseCode;// 交易代码
    private String enterpriseLogo;//企业logo(本地地址)
    private String enterpriseAlogo;// app端logo(本地地址)
    private String enterpriseType;// 所属行业类型
    private Date enterpriseFounding;// 成立年分
    private String enterpriseCeo;// ceo
    private String enterpriseAddress;// 企业地址
    private String enterpriseDesc;// 企业介绍
    private Double enterpriseSprice;// 最近成交价格
    private Double enterpriseRate;// 费率
    private Double enterpriseItprice;//原始股价格

    public EnterpriseInfo(Integer enterpriseId, String enterpriseName, String enterpriseCode, Double enterpriseSprice, Double enterpriseRate) {
        this.enterpriseId = enterpriseId;
        this.enterpriseName = enterpriseName;
        this.enterpriseCode = enterpriseCode;
        this.enterpriseSprice = enterpriseSprice;
        this.enterpriseRate = enterpriseRate;
    }

    public EnterpriseInfo(String enterpriseName, String enterpriseCode, String enterpriceLogo, String enterpriceAlogo, String enterpriceType, Date enterpriceFounding, String enterpriceCeo, String enterpriceAddress, String enterpriceDesc, Double enterpriseRate) {
        this.enterpriseName = enterpriseName;
        this.enterpriseCode = enterpriseCode;
        this.enterpriseLogo = enterpriceLogo;
        this.enterpriseAlogo = enterpriceAlogo;
        this.enterpriseType = enterpriceType;
        this.enterpriseFounding = enterpriceFounding;
        this.enterpriseCeo = enterpriceCeo;
        this.enterpriseAddress = enterpriceAddress;
        this.enterpriseDesc = enterpriceDesc;
        this.enterpriseRate = enterpriseRate;
    }

    public EnterpriseInfo(ResultSet rs) {
        try {
            this.enterpriseName = rs.getString("enterprise_name");
            this.enterpriseCode = rs.getString("enterprise_code");
            this.enterpriseSprice = rs.getDouble("enterprise_sprice");
            this.enterpriseRate = rs.getDouble("enterprise_rate");
            this.enterpriseId = rs.getInt("enterprise_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
