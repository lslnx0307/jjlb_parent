package com.jjlb.dao.funds;

import com.jjlb.model.entity.member.DrCompanyFundsLog;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


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
	public void insertDrCompanyFundsLog(DrCompanyFundsLog drCompanyFundsLog) throws SQLException;
	
	
	/**
	 * 得到公司收支记录数据
	 * @param map
	 * @return List<DrCompanyFundsLog>
	 */
    public List<DrCompanyFundsLog> getDrCompanyFundsLogList(Map<String, Object> map);
   
	/**
	 * 得到公司收支记录数据总数
	 * @param map
	 * @return Integer
	 */
    public Integer getDrCompanyFundsLogCounts(Map<String, Object> map);
    
 	/**
 	 * 统计支出收支
 	 * @param map
 	 * @return BigDecimal
 	 * @throws SQLException
 	 */
     public BigDecimal getDrCompanyFundsLogSum(Map<String, Object> map) throws SQLException;
     
 	/**
 	 * 更新数据
 	 * @param drCompanyFundsLog
 	 * @return void
 	 * @throws SQLException
 	 */
 	public void updateDrCompanyFundsLog(DrCompanyFundsLog drCompanyFundsLog) throws SQLException;
 	
	/**
	 * 插入数据
	 * @param drCompanyFundsLog
	 * @return void
	 * @throws SQLException
	 */
	public void insertDrCompanyFundsLogFK(DrCompanyFundsLog drCompanyFundsLog) throws SQLException;
	
	public void updateDrCompanyFundsLogByPid(DrCompanyFundsLog drCompanyFundsLog);
}