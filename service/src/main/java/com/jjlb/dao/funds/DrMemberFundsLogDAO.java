package com.jjlb.dao.funds;

import com.jjlb.model.entity.funds.DrMemberFundsLog;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;


/**
 * @author lslnx0307
 */
@Repository
public interface DrMemberFundsLogDAO {

	/**
	 * 插入数据
	 * @param DrMemberFundsLog
	 * @return void
	 * @throws SQLException
	 */
    public void insertDrMemberFundsLog(DrMemberFundsLog DrMemberFundsLog) throws SQLException;
    
	

}