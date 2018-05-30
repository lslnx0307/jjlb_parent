package com.jjlb.dao.product;

import com.jjlb.model.entity.product.DrProductInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author lslnx0307
 */
@Repository
public interface DrProductInfoDAO {

	/**
	 * 只修改已募集金额，剩余金额，产品状态 通过主键修改
	 * 
	 * @param info
	 *            DrProductInfo
	 */
	public void updateProductSelective(DrProductInfo info);


	/**
	 * 根据产品id获得 In_cust_no (借款人或企业的账户)
	 * 
	 * @param id
	 * @return
	 */
	public String getIn_cust_noByProductId(Integer id);

	/**
	 * 根据id获取 DrProductInfo
	 * @param id
	 * @return
	 */
	public DrProductInfo selectProductById(Integer id);

	/**
	 * 查询募集完成的产品 以及募集中的新手产品
	 * @return
	 */
	public List<DrProductInfo> selectRaiseSuccesProductInfo();

	/**
	 * 查询要到期的产品
	 * @return
	 */
	public List<DrProductInfo> selectExpireProductInfo();

	/**
	 * 通过PID修改产品状态
	 * @return void
	 * @throws SQLException;
	 */
	public void updateDrProductInfoStatusById(@Param("status") Integer status, @Param("pid") Integer pid)
			throws SQLException;

	/**
	 * 更新产品回款状态
	 * @param  map
	 * @return void
	 * @throws SQLException;
	 */
	public void updateDrProductInfoInterestRepay(Map<String, Object> map);
}