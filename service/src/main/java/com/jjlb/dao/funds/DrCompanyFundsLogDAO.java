package com.jjlb.dao.funds;

import com.jjlb.model.entity.member.DrCompanyFundsLog;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;


/**
 * @author lslnx0307
 */
@Repository
public interface DrCompanyFundsLogDAO {
	/**
	 * 插入数据
	 * @param drCompanyFundsLog
	 * @return void
	 * @throws SQLException
	 */
	 void insertDrCompanyFundsLog(DrCompanyFundsLog drCompanyFundsLog) throws SQLException;
	
	

}