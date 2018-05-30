package com.jjlb.dao.funds;

import com.jjlb.model.entity.funds.JsCompanyAccountLog;
import org.springframework.stereotype.Repository;


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

}
