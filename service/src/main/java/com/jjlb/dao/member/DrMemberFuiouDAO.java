package com.jjlb.dao.member;

import com.jjlb.model.entity.member.DrMemberFuIou;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;


/**
 * @author lslnx0307
 */
@Repository
public interface DrMemberFuiouDAO {

	/**
	 * 添加
	 * @param drMemberFuIou
	 * @throws SQLException
	 */
	void insertDrMemberFuiou(DrMemberFuIou drMemberFuIou) throws SQLException;

	/**
	 * 通过平台用户id获取DrMemberFuiou
	 * @param uid
	 * @return
	 */
	public DrMemberFuIou getDrMemberFuiouByUserId(Integer uid);

	/**
	 * 通过fuiou平台账户获取DrMemberFuiou
	 * @param fuiouId
	 * @return
	 */
	public DrMemberFuIou getDrMemberFuiouByFuiouId(String fuiouId);
}
