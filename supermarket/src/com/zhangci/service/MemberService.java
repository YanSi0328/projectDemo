package com.zhangci.service;

import com.zhangci.common.ServiceResponse;
import com.zhangci.entity.Member;

/**
 * ClassName: MemberService
 * <p>
 * Author: ZhangCi
 * Description:
 * Date: 2021/4/14 22:51
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface MemberService {

    /**
     *
     * @param member
     */
    /**
     * 新增会员信息
     *
     * @param member 会员对象
     * @return 操作是否成功
     */
    ServiceResponse addMember(Member member);

    /**
     * 修改会员信息
     *
     * @param member 会员对象
     * @return 操作是否成功
     */
    ServiceResponse updateMember(Member member);


    /**
     * 查询全部的会员信息
     */
    void selectMember();


    /**
     * 根据id删除会员信息
     *
     * @return 操作是否成功
     */
    ServiceResponse deleteMemberById();

    /**
     * 会员余额充值
     *
     * @return 操作是否成功
     */
    ServiceResponse mbBalanceAdd();
}
