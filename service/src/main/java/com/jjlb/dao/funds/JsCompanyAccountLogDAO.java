package com.jjlb.dao.funds;

import com.jjlb.model.entity.funds.JsCompanyAccountLog;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * @author lslnx0307
 */
@Repository
public interface JsCompanyAccountLogDAO {

	/**
	 * 插入
	 * @param accountLog
	 */
	public void insertCompanyAccountLog(JsCompanyAccountLog accountLog);

	/**
	 * 获取log列表
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getCompanyAccountLog(Map<String, Object> map);

	/**
	 *
	 * @param map
	 * @return
	 */
	public int getCompanyAccountLogCount(Map<String, Object> map);
	
	/**
	 * 统计收入
	 * @param map
	 * @return
	 */
	public BigDecimal getCompanyAccountSRSum(Map<String, Object> map);
	
	/**
	 * 统计支出
	 * @param map
	 * @return
	 */
	public BigDecimal getCompanyAccountZCSum(Map<String, Object> map);
	
	/**
	 * 修改
	 */
	public void updateCompanyAccountLog(JsCompanyAccountLog accountLog);
	
	/**
	 * 查询存管最新余额
	 * @return
	 */
	public BigDecimal getBlanceByFuiou();
	
	/**
	 * 查询金运通最新余额
	 * @return
	 */
	public BigDecimal getBlanceByJYT();

}
