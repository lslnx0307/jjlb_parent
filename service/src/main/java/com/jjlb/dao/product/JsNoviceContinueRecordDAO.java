package com.jjlb.dao.product;

import com.jjlb.model.entity.product.JsNoviceContinueRecord;
import org.springframework.stereotype.Repository;


/**
 * @author lslnx0307
 */
@Repository
public interface JsNoviceContinueRecordDAO {
	/**
	 * 插入
	 * @param jsNoviceContinueRecord
	 */
	public void insert(JsNoviceContinueRecord jsNoviceContinueRecord);
	
	
}
