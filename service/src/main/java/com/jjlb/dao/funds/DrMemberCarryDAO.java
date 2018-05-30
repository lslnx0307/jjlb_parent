package com.jjlb.dao.funds;

import com.jjlb.model.entity.funds.DrMemberCarry;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * @author lslnx0307
 */
@Repository
public interface DrMemberCarryDAO {
	/**
	 * 添加提现
	 * @param drMemberCarry
	 * @return void
	 * @throws SQLException
	 */
	void insertDrMemberCarry(DrMemberCarry drMemberCarry) throws SQLException;


	/**
	 * 根据uid和type查询用户的提现金额
	 * @param uid
	 * @param type
	 * @return
	 */
	BigDecimal selectCarryAmountCount(@Param(value = "uid") Integer uid, @Param(value = "type") Integer type);
    

}