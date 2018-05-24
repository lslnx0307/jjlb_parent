package com.jjlb.dao.funds;

import com.jjlb.model.entity.funds.DrMemberFunds;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;


/**
 * @author lslnx0307
 * @date 2018/5/23
 */
@Repository
public interface DrMemberFundsDAO {
	
	/**
	 * @Description 根据id查询[前台也在用]
	 * @param uid
	 * @return DrMemberFunds
	 * @throws 
	 */
    public DrMemberFunds queryDrMemberFundsByUid(int uid);
    
	/**
	 * @Description 修改
	 * @param drMemberFunds
	 * @return void
	 * @throws SQLException
	 */
    public void updateDrMemberFunds(DrMemberFunds drMemberFunds) throws SQLException; 
    
	/**
	 * @Description 添加
	 * @param drMemberFunds
	 * @return void
	 * @throws SQLException
	 */
    public void insertDrMemberFunds(DrMemberFunds drMemberFunds) throws SQLException; 
    
}