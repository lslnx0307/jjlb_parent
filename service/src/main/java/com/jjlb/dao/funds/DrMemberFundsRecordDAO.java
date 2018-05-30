package com.jjlb.dao.funds;

import com.jjlb.model.entity.funds.DrMemberFundsRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;


/**
 * @author lslnx0307
 */
@Repository
public interface DrMemberFundsRecordDAO {

	/**
	 * 添加记录
	 * @param record
	 * @throws SQLException
	 */
	public void insert(DrMemberFundsRecord record) throws SQLException;

	/**
	 * 查找交易记录
	 * @param investId 投资ID 为NULL 则根据orderNo查询
	 * @param status 支付订单编号 为NULL 则根据investId查询
	 * @return 查询到的交易记录
	 */
	public DrMemberFundsRecord selectByParamForProductInterest(@Param("investId") Integer investId,
															   @Param("status") Integer status);
	/**
	 * 查找交易记录
	 * @param investId 投资ID 为NULL 则根据orderNo查询
	 * @param orderNo 支付订单编号 为NULL 则根据investId查询
	 * @return 查询到的交易记录
	 */
	public DrMemberFundsRecord selectByParam(@Param("investId") Integer investId, @Param("orderNo") String orderNo);
}
