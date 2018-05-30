package com.jjlb.dao.member;


import com.jjlb.model.entity.member.DrMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @author lslnx0307
 */
@Repository
public interface DrMemberDAO {

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


}