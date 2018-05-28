package com.jjlb.dao.funds;

import com.jjlb.model.entity.sys.JsMerchantMarketing;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 商户营销交易流水
 * @author DELL
 *
 */
@Repository
public interface JsMerchantMarketingDAO {
	
	/**
	 *  根据map查询
	 * @param map
	 * @return
	 */
	public List<JsMerchantMarketing> selectObjectByMap(Map<String, Object> map);
	
	/**
	 * 添加
	 * @param obj
	 */
	public void insert(JsMerchantMarketing obj);
	/**
	 * 批量添加
	 * @param list
	 */
	public void insertBatch(List<JsMerchantMarketing> list);
	
	/**
	 * 修改
	 * @param obj
	 */
	public void update(JsMerchantMarketing obj);
}
