package com.jjlb.dao.funds;

import com.jjlb.model.entity.funds.DrMemberFunds;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;


/**
 * @author lslnx0307
 * @date 2018/5/23
 */
@Repository
public interface DrMemberFundsDAO {
	
	/**
	 *  根据id查询[前台也在用]
	 * @param uid
	 * @return DrMemberFunds
	 * @throws 
	 */
    public DrMemberFunds queryDrMemberFundsByUid(int uid);
    
	/**
	 *  修改
	 * @param drMemberFunds
	 * @return void
	 * @throws SQLException
	 */
    public void updateDrMemberFunds(DrMemberFunds drMemberFunds) throws SQLException;


	/**
	 * 回款方法使用
	 * @param list
	 */
	void batchUpdateDrMemberFunds(List<DrMemberFunds> list);
    
}