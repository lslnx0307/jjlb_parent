package com.jjlb.dao.product;

import com.jjlb.model.entity.product.DrProductInvestRepayInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author lslnx0307
 */
@Repository
public interface DrProductInvestRepayInfoDAO {
	
	public Integer selectInvestRepayInfoNumsByParam(Map<String, Object> map);
	/**
	 * 查询回款记录
	 * @param investId
	 * @return
	 */
	public List<Map<String,Object>> selectRepayInfoDetailByInvestId(Integer investId);
	
	/**
	 *未来7天回款笔数
	 * @param uid
	 * @return
	 */
	public int selectReturnedCount(Integer uid);
	
	/**
	 * 未来7天回款总和
	 * @param uid
	 * @return
	 */
	public List<DrProductInvestRepayInfo> selectPayment(Integer uid);
	
	public void update(DrProductInvestRepayInfo drProductInvestRepayInfo);

	/**
	 * 待还款记录
	 * @return
	 */
	public List<DrProductInvestRepayInfo> selectShouldRepayInfo(@Param("pid")Integer pid);

	/**
	 * 修改还款记录（恒丰存管）
	 * @param repayinfo
	 * @throws Exception
	 */
	public void updateByIdFuiou(DrProductInvestRepayInfo repayinfo)throws Exception;
	
}
