package com.jjlb.dao.product;

import com.jjlb.model.entity.product.DrProductInvestRepayInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lslnx0307
 */
@Repository
public interface DrProductInvestRepayInfoDAO {
	

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
