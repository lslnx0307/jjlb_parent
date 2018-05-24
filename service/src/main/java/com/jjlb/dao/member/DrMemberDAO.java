package com.jjlb.dao.member;


import com.jjlb.model.entity.member.DrMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * @author lslnx0307
 */
@Repository
public interface DrMemberDAO {
	/**
	 * 新增用户
	 * @param DrMember
	 * @throws SQLException
	 */
	public void insertDrMember(DrMember DrMember)throws SQLException;

	/**
	 * 更新用户
	 * @param record
	 * @return
	 */
    public int updateByPrimaryKey(DrMember record);

	/**
	 * 根据用户id查询
	 * @param uid
	 * @return
	 */
	public DrMember selectByPrimaryKey(Integer uid);

	/**
	 * 根据登陆手机号获取drmember
	 * @param loginId
	 * @return
	 */
    public DrMember selectDrMemberByMobilephone(@Param("loginId") String loginId);

	/**
	 * 查询被用户推荐注册人的总数
	 * @param uid
	 * @return
	 */
	Integer selectRecommRegSum(Integer uid);
    
    /**
	 * 获取用户待还和待收笔数
	 * @param map
	 * @return
	 */
	public Map<String,Integer> queryCollectCountAndStayCount(Map<String, Object> map);
	
	/**
	 * 通过推荐码或者手机号获取用户
	 * @param str
	 * @return
	 */
	public DrMember selectDrMemberByMobileOrRecomm(@Param("str") String str);

	/**
	 *根据用户uid查询渠道用户参与首投奖励的用户
	 * @param uid
	 * @return
	 */
	public List<DrMember> selectMemberIsJoinActivity(@Param("uid") Integer uid);

	/**
	 * 根据手机号查询条数
	 * @param mobilephone
	 * @return
	 */
	public Integer isExistsMobilphone(@Param("mobilephone") String mobilephone);
	/**
	 * 通过推荐码获取用户脱敏手机号
	 * @param recommCode
	 * @return
	 */
	public String selectMobilePhoneByRecommCode(@Param("recommCode") String recommCode);
	
	/**
	 * 用户是否首投
	 * @param uid
	 * @return
	 */
	public int selectFirstPayMent(Integer uid);
	/**
	 * 查询 最近注册100条用户,手机号,注册时间
	 * @return
	 */
	public List<Map<String,Object>> lastRegMember();

	/**
	 * 不知道这个方法是干嘛的
	 * @return
	 */
	public Map<String,Object> selectIndexSummaryData();
	
	/**
	 * 查询总注册人数
	 * @return
	 */
	public int selectDrmembercount();
	/**
	 * 查询用户是不是当月注册的
	 * @return
	 */
	public DrMember selectDrMemberByMonth(Integer uid);
	/**
	 * 根据uid查询用户是否为cps
	 * @param uid
	 * @return
	 */
	public Integer checkMemberCPS(Integer uid);

	/**
	 * 查询根据用户uid和渠道判断是否属于该渠道
	 * @param map
	 * @return
	 */
	public DrMember selectIsToFrom(Map<String, Object> map);

	/**
	 * 查询用户是哪个渠道的
	 * @param map
	 * @return
	 */
	public Map<String,Object> selectMemberFrom(Map<String, Object> map);


}