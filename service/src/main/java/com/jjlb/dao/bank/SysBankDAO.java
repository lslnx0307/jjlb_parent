package com.jjlb.dao.bank;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @author lslnx0307
 */
@Repository
public interface SysBankDAO {
    

	/**
	 * 查询
	 * @param bankCode
	 * @return
	 */
	 String selectBankByCode(@Param("bankCode") String bankCode);
}