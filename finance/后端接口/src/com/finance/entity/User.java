package com.finance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * ClassName: USer
 * Author: ZhangCi
 *
 * @description:
 * @date: 2021/5/20 22:08
 * @version: 0.1
 * @since: 1.8
 */
@Getter
@Setter
@NoArgsConstructor
public class User {

    private Integer uid;            // 用户id
    private String username;       // 用户昵称
    private String headPic;        // 用户头像
    private String headPicThumb;   // 用户头像缩略图
    private String des;             // 个性签名
    private Integer sex;            // 性别
    private Date birthday;         // 出生日期
    private String avatarColor;    // 爱好
    private String phone;           // 联系电话
    private Integer grade;          // 等级

    // 用户建议
    private String troubleList;     // 用户遇到的问题
    private String suggestion;      // 用户建议
    private String contact;         // 联系方式
    private String platform;        // 平台来源：1->IOS，2->Android

    // 个人信息接口
    public User(Integer uid, String username, String headPic, String headPicThumb, String des, Integer sex, Date birthday, String avatarColor, String phone, Integer grade) {
        this.uid = uid;
        this.username = username;
        this.headPic = headPic;
        this.headPicThumb = headPicThumb;
        this.des = des;
        this.sex = sex;
        this.birthday = birthday;
        this.avatarColor = avatarColor;
        this.phone = phone;
        this.grade = grade;
    }


    // 意见反馈接口
    public User(Integer uid, String troubleList, String suggestion, String contact, String platform) {
        this.uid = uid;
        this.troubleList = troubleList;
        this.suggestion = suggestion;
        this.contact = contact;
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", headPic='" + headPic + '\'' +
                ", headPicThumb='" + headPicThumb + '\'' +
                ", des='" + des + '\'' +
                ", sex=" + ((sex == 1) ? "男" : "女") +
                ", birthday=" + birthday +
                ", avatarColor='" + avatarColor + '\'' +
                ", phone='" + phone + '\'' +
                ", grade=" + grade +
                ", troubleList='" + troubleList + '\'' +
                ", suggestion='" + suggestion + '\'' +
                ", contact='" + contact + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }
}
