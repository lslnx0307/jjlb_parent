package com.jjlb.dao.product;

import com.jjlb.model.entity.product.JsActivityProductInvestInfo;

import java.util.List;
import java.util.Map;



public interface JsActivityProductInvestInfoDAO {
	/**
	 * 插入
	 * @param jsActivityProductInvestInfo
	 */
	public void insert(JsActivityProductInvestInfo jsActivityProductInvestInfo);
	/**
	 * 根据 Map查询投资活动记录
	 * @param map
	 * @return
	 */
	public String selectjsActivityProductLuckCodes(Map<String, Object> map);
	/**
	 * 根据 Map查询投资抽奖码和是否中奖
	 * @param map
	 * @return
	 */
	public Map<String, Object> selectjsActivityProductLuckCodesAndStatus(Map<String, Object> map);
	
	/**
	 * 根据产品id查询活动投资记录 (身份证手机已脱敏)
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>>  selectJsActivityInvestList(Map<String, Object> map);
	
	/**
	 * 查询 投资 记录
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>>  selectJsActivityProductInvestByParam(Map<String, Object> map);
	
	/**
	 * 查询  每期的中奖者
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>>  selectJsActivityProductIsWinner(Map<String, Object> map);
	/**
	 * 查询  中奖者count
	 * @param map
	 * @return
	 */
	public int  selectJsActivityProductIsWinnerCount(Map<String, Object> map);
		
	/**
	 * 查询中奖记录
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>>  selectjsActivityProductInvestInfoList(Map<String, Object> map);
	/**
	 * 查询中奖记录总条数
	 * @param map
	 * @return
	 */
	public int selectjsActivityProductInvestInfoCount(Map<String, Object> map);
	/**
	 * iphone7 活动总投资人数,活动中奖总人数
	 * @return
	 */
	public Map<String,Object> iPhoneCensus();
	/**
	 * iphone7 活动最新动态
	 * @return
	 */
	public List<Map<String,Object>> selectNewTrends();
	
}
