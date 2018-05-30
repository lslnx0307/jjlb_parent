package com.jjlb.dao.funds;

import com.jjlb.model.entity.sys.JsMerchantMarketing;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 商户营销交易流水
 * @author DELL
 *
 */
@Repository
public interface JsMerchantMarketingDAO {
	

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
	
}
