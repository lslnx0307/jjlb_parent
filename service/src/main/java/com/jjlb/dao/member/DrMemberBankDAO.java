package com.jjlb.dao.member;

import com.jjlb.model.entity.member.DrMemberBank;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * @author lslnx0307
 */
@Repository
public interface DrMemberBankDAO {
	
	/**
	 * 添加银行卡
	 * @param drMemberBank
	 * @return void
	 * @throws SQLException
	 */
    void insertDrMemberBank(DrMemberBank drMemberBank) throws SQLException;

    /**
	 * 查询存管认证的卡
	 * @param uid
	 * @return DrMemberBank
	 * @throws 
	 */
    public DrMemberBank selectFuiouIdentificationBank(int uid);

}