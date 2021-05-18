package com.zhangci.entity;

import lombok.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * ClassName: Member
 * <p>
 * Author: ZhangCi
 * Description:会员信息表
 * Date: 2021/4/12 21:34
 * Version: 0.1
 * Since: JDK 1.8
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Serializable {

    private static final long serialVersionUID = 54288223524496644L;
    private int memberId;               //会员编号 int 自增 唯一 主键
    @NonNull
    private String memberName;          //会员名 varchar（20） 非空
    private String memberPassword;      //密码  varchar(64) 加密存储
    private String memberHeadImage;     //头像  varchar(100)
    private String memberPhone;         //联系方式 varchar（20）可空
    private float memberIntegral;       //积分  float 默认为0
    private double memberBalance;       //余额。 decimal默认为0
    private LocalDateTime memberCreateTime;    //创建时间 datetime/timestamp 可空
    private LocalDateTime memberUpdateTime;    //修改时间 datetime/timestamp 可空


    public Member(@NonNull String memberName, String memberPassword, String memberHeadImage, String memberPhone) {
        this.memberName = memberName;
        this.memberPassword = memberPassword;
        this.memberHeadImage = memberHeadImage;
        this.memberPhone = memberPhone;
    }

    //结果集
    public Member(ResultSet rs) throws SQLException {
        this(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getString("head_image"),
                rs.getString("phone"),
                rs.getFloat("integral"),
                rs.getDouble("balance"),
                (LocalDateTime) rs.getObject("create_time"),
                (LocalDateTime) rs.getObject("update_time")
        );
    }
}
