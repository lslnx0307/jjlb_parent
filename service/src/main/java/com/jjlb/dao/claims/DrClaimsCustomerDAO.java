package com.jjlb.dao.claims;

import com.jjlb.model.entity.claims.DrClaimsCustomer;
import org.springframework.stereotype.Repository;


/**
 * @author lslnx0307
 */
@Repository
public interface DrClaimsCustomerDAO {
	

	 /**
 	 * 根据条件得到企业客户基本信息
 	 * @param lid
 	 * @return DrClaimsCustomer
 	 */
	 DrClaimsCustomer getDrClaimsCustomerByLid(Integer lid);
     

}