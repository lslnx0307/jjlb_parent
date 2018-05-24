package com.jjlb.dao.product;

import java.util.List;
import java.util.Map;

import com.jjlb.model.product.JsProductPrizeLog;

public interface JsProductPrizeLogDAO {
	/**
	 * 查询投资记录
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectListMap(Map<String, Object> map);
	/**
	 * 查询投资记录
	 * @param map
	 * @return
	 */
	public int selectListMapCount(Map<String, Object> map);
	
	/**
	 * 查询投资记录
	 * @param map
	 * @return
	 */
	public List<JsProductPrizeLog> selectPrizeLogByUid(Map<String, Object> map);
	/**
	 * 插入奖品记录
	 * @param jsProductPrizeLog
	 */
	public void insert(JsProductPrizeLog jsProductPrizeLog);
}
