package com.jjlb.dao.claims;

import com.jjlb.model.entity.claims.DrClaimsLoan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 债权dao
 * @author lslnx0307
 */
@Repository
public interface DrClaimsLoanDAO {


	/**
	 * 根据id查询drClaimsLoan
	 * @param sid
	 * @return
	 */
	DrClaimsLoan getDrClaimsLoanBySid(@Param("sid") Integer sid);

	
}