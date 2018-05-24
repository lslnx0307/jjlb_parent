package com.jjlb.dao.funds;

import com.jjlb.model.entity.funds.DrMemberFundsRecord;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * @author lslnx0307
 */
@Repository
public interface DrMemberFundsRecordDAO {
	
	public void insert(DrMemberFundsRecord record) throws SQLException;
	
	/**
	 * 查找符合条件的资产记录
	 * @param map
	 * @return
	 */
	public List<DrMemberFundsRecord> selectMemberFundsRecordListByParam(Map<String, Object> map);
	
	public Integer selectMemberFundsRecordListCountByParam(Map<String, Object> map);
	/**
	 * 根据订单号修改
	 * @param record
	 * @return void
	 */
	public void updateStatusByNo(DrMemberFundsRecord record) throws SQLException;
	/**
	 * 查询累计收益
	 * @param map
	 * @return
	 */
	public List<DrMemberFundsRecord> selectAccumulatedIncomeByUid(Map<String, Object> map);
	/**
	 * 查询累计收益Count
	 * @param map
	 * @return
	 */
	public Integer selectAccumulatedIncomeByUidCount(Map<String, Object> map);
	/**
	 * 查询累计收益sum
	 * @param map
	 * @return
	 */
	public BigDecimal selectAccumulatedIncomeSumByUid(Map<String, Object> map);
	
	/**
	 * 根据流水号查询
	 * @param orderNo
	 * @return
	 */
	public DrMemberFundsRecord selectMemberFundsRecordByOrderNo(String orderNo);
}
