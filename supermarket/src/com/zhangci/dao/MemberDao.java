package com.zhangci.dao;

import com.zhangci.entity.Member;

import java.util.List;
import java.util.Map;

/**
 * ClassName: MemberDao
 * <p>
 * Author: ZhangCi
 * Description: 会员管理dao
 * Date: 2021/4/13 19:21
 * Version: 0.1
 * Since: JDK 1.8
 */
public interface MemberDao {

    /**
     * 新增会员
     *
     * @param member 会员对象
     * @return 影响sql的记录次数
     */
    int addMember(Member member);

    /**
     * 根据id修改会员信息
     *
     * @param params 多个参数
     * @param id     指定会员id
     * @return 影响sql测记录数
     */
    int updateMember(Map<String, Object> params, int id);

    /**
     * 查询所有的会员信息
     *
     * @return 会员对象，便于在service层赋值
     */
    List<Member> selectMember();

    /**
     * 根据id删除指定会员
     *
     * @param id 会员id
     * @return 影响sql的操作次数
     */
    int deleteMember(int id);

    /**
     * 根据 id 增加会员余额
     *
     * @param money 充值金额
     * @param id    会员id
     * @return 受影响的sql条数
     */
    int updateMemberBalance(int money, int id);

    /**
     * 根据id查询会员表信息
     *
     * @param id 会员id
     * @return 会员实例
     */
    Member selectMemberById(int id);

}
