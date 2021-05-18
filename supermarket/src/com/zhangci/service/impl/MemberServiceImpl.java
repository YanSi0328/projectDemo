package com.zhangci.service.impl;

import com.zhangci.common.ServiceResponse;
import com.zhangci.dao.MemberDao;
import com.zhangci.dao.impl.MemberDaoImpl;
import com.zhangci.entity.Member;
import com.zhangci.service.MemberService;
import com.zhangci.util.FileUtil;
import com.zhangci.util.InputUtil;
import com.zhangci.util.MD5Util;
import com.zhangci.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * ClassName: MemberServiceImpl
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/14 22:52
 * Version: 0.1
 * Since: JDK 1.8
 */
public class MemberServiceImpl implements MemberService {
    private static MemberDao memberDao;
    private static InputUtil inputUtil;

    static {
        memberDao = new MemberDaoImpl();
        inputUtil = new InputUtil();
    }

    //测试： 张词,111111111111,D:\workspace\one\supermarket\files\imgs\2045431.jpg,13526373149
    @Override
    public ServiceResponse addMember(Member member) {
        selectMember();
        System.out.println("会员信息录入格式: 名称,密码(10-20),头像,联系方式");
        System.out.print("请录入会员各项信息: ");
        String line = inputUtil.nextLine();
        String[] split = line.split(",");

        //获取名称、密码、头像、联系方式
        String name = split[0];
        String pass = split[1];
        String headImg = split[2];
        String phone = split[3];

        //判断密码长度是否是十位
        if ((pass.length() < 10) || (pass.length() > 20)) {
            return ServiceResponse.error("密码长度必须在10至20位");
        }
        //对获得的密码进行加密
        pass = MD5Util.md5EncodePassword(pass);

        //对头像地址是否存在进行判断
        //注意只能使用绝对路径
        String imgFile = FileUtil.dealFile(headImg);
        //对电话号码的处理
        if (phone.length() != 11) {
            return ServiceResponse.error("电话号码必须为11位");
        }

        //将加密后的密码、存在的头像地址、正确的电话号码存入对象
        member = new Member(name, pass, imgFile, phone);

        //传回dao
        int record = memberDao.addMember(member);
        if (record != 1) {
            return ServiceResponse.error();
        }
        System.out.println("会员 " + name + " 信息新增成功");
        return ServiceResponse.success();

    }

    /**
     * 有选择的更新会员信息
     *
     * @param member 会员对象
     */
    @Override
    public ServiceResponse updateMember(Member member) {
        selectMember();
        System.out.println("声明：每次每个字段仅支持单次更改，可以更改多个字段，请按照格式录入");
        System.out.println("修改录入格式: 要修改的字段名=修改后的数据,要修改的字段名=修改后的数据");
        System.out.println();
        System.out.print("请录入要修改的会员编号: ");
        int memberId = inputUtil.nextInt();

        Map<String, Object> memberMap = new HashMap<>(16);
        System.out.println("修改录入格式: 名称=,密码=");
        System.out.print("请录入要修改的会员信息: ");
        String line = inputUtil.nextLine(1);
        //第一次分割拿到单个字段的键值对
        String[] outSplit = line.split(",");

        //第二次分割分别拿到单个字段的建与值
        for (String anOutSplit : outSplit) {

            //使用数组存放单个键值对
            String[] inSplit = anOutSplit.split("=");
            //拿到键值对
            String key = inSplit[0];
            String values = inSplit[1];

            //对键进行判断   名称 = juvu
            switch (key) {
                case "名称":
                    //修改会员名称
                    memberMap.put("name", values);
                    break;
                case "密码":
                    //修改会员密码
                    memberMap.put("password", MD5Util.md5EncodePassword(values));
                    break;
                case "头像":
                    //修改会员头像
                    memberMap.put("head_image", FileUtil.dealFile(values));
                    break;
                case "联系方式":
                    //修改会员联系方式
                    memberMap.put("phone", StringUtil.storePhone(values));
                    break;
            }
        }
        int record = memberDao.updateMember(memberMap, memberId);
        if (record != 1) {
            return ServiceResponse.error();
        }
        System.out.println("修改: " + memberMap.toString());
        return ServiceResponse.success();
    }

    @Override
    public void selectMember() {
        List<Member> memberList = memberDao.selectMember();

        System.out.println("|————————————————————————————————————————————————————————————————————————————————————————————————|");
        System.out.println("|                                       会  员   信   息   表                                    |");
        System.out.println("|________________________________________________________________________________________________|");
        System.out.println("| 编号   名称    密码    头像  联系方式 积分  余额    创 建 时 间           更 新 时 间            |");
        System.out.println("|________________________________________________________________________________________________|");

        //电话号码的显示
        String phone;
        //密码的显示
        String pass;

        for (Member member : memberList) {
            //处理电话号码和密码的显示方式
            phone = StringUtil.dealPhoneNum(member.getMemberPhone());
            pass = StringUtil.showPass(member.getMemberPassword());

            System.out.println("|  " + member.getMemberId() + "\t|"
                    + member.getMemberName() + "\t|" + pass + "\t|"
                    + member.getMemberHeadImage() + "\t|" + phone + "\t|"
                    + member.getMemberIntegral() + "\t|" + member.getMemberBalance() + "\t|"
                    + member.getMemberCreateTime() + "\t|" + member.getMemberUpdateTime() + " |");
        }
        System.out.println("|________________________________________________________________________________________________|");
        System.out.println();
        System.out.println("=======>>>  查询完毕~~~");
    }

    @Override
    public ServiceResponse deleteMemberById() {
        selectMember();
        System.out.println();
        System.out.print("请输入要删除的会员id: ");
        int mId = inputUtil.nextInt();
        int result = memberDao.deleteMember(mId);

        //判断删除是否成功
        if (result != 1) {
            return ServiceResponse.error("编号不正确，请确认后输入");
        }
        return ServiceResponse.success("======>>>>编号为 " + mId + " 的会员信息删除成功！！");
    }

    @Override
    public ServiceResponse mbBalanceAdd() {
        selectMember();

        System.out.print("请录入要充值的会员id: ");
        int memberId = inputUtil.nextInt();
        System.out.print("请录入要充值的金额: ");
        int money = inputUtil.nextInt();
        int record = memberDao.updateMemberBalance(money, memberId);
        if (record != 1) {
            return ServiceResponse.error();
        }
        System.out.println("会员 " + memberId + " 充值 " + money + "操作成功");
        return ServiceResponse.success();
    }
}
