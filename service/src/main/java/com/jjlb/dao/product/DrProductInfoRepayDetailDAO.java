package com.jjlb.dao.product;

import com.jjlb.model.entity.product.DrProductInfoRepayDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author lslnx0307
 */
@Repository
public interface DrProductInfoRepayDetailDAO {

	/**
	 * 批量插入回款记录
	 * @param list
	 */
	public void batchInsert(@Param("list") List<DrProductInfoRepayDetail> list);
	
	/**
	 * 修改还款状态
	 * @param productRepayinfoDetail
	 * @throws Exception
	 */
	public void updateById(DrProductInfoRepayDetail productRepayinfoDetail)throws Exception;
	/**
	 * 按月付息产品
	 * @param pid
	 * @return
	 */
	public List<DrProductInfoRepayDetail> selectByPid(@Param("pid") Integer pid);
	/**
	 * 查询按月付息产品当前未还款的期数
	 * @param pid
	 * @return
	 */
	public DrProductInfoRepayDetail selectEarliestUnreimbursement(Integer pid);
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public DrProductInfoRepayDetail selectById(@Param("pid") Integer id);
	
	
	/**
	 * 按月付息产品
	 * @param map
	 * @return
	 */
	public List<DrProductInfoRepayDetail> getrepayDetailById(Map<String, Object> map);
	
	public void updateLoanStatusById(Map<String, Object> map);

}
