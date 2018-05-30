package com.jjlb.dao.funds;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author lslnx0307
 * @date
 */
@Repository
public interface JsMemberRewardsDAO {

	/**
	 * 查询剩余免费提现次数
	 * @param uid
	 * @return
	 */
	public int getQuantityCount(@Param("uid") Integer uid);
}
