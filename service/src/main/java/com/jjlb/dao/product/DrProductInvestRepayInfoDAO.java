package com.jjlb.dao.product;

import java.util.List;
import java.util.Map;

import com.jjlb.model.product.DrProductInvestRepayInfo;

public interface DrProductInvestRepayInfoDAO {
	
	public Integer selectInvestRepayInfoNumsByParam(Map<String, Object> map);
	/**
	 * 查询回款记录
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectRepayInfoDetailByInvestId(Integer investId);
	
	/**
	 *未来7天回款笔数
	 * @param uid
	 * @return
	 */
	public int selectReturnedCount(Integer uid);
	
	/**
	 * 未来7天回款总和
	 * @param uid
	 * @return
	 */
	public List<DrProductInvestRepayInfo> selectPayment(Integer uid);
	
	public void update(DrProductInvestRepayInfo drProductInvestRepayInfo);
	
}