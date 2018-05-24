package com.jjlb.dao.funds;

import com.jjlb.model.entity.funds.JsMemberRewards;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface JsMemberRewardsDAO {
	
	/**
	 * 根据会员ID获取对应权益
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getMemberRewardsByMgid(Map<String, Object> map);
	
	/**
	 * 查询剩余免费提现次数
	 * @param uid
	 * @return
	 */
	public int getQuantityCount(@Param("uid") Integer uid);
	/**
	 * 根据id获得奖励和等级
	 * @param id
	 * @return
	 */
	JsMemberRewards getJsMemberRewardById(Integer id);
	
	/**
	 * 获取list
	 * @param map
	 * @return
	 */
	public List<JsMemberRewards> selectJsMemberRewardsList(Map<String, Object> map);
}
