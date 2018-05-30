package com.jjlb.dao.member;

import com.jjlb.model.entity.member.DrMemberFavourable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author lslnx0307
 */
@Repository
public interface DrMemberFavourableDAO {
	/**
	 * 获取用户优惠券列表
	 * @param map
	 * @return
	 */
	public List<DrMemberFavourable> getMemberFavourableByParam(Map<String, Object> map);

	/**
	 * 更新优惠券
	 * @param drMemberFavourable
	 */
	public void updateFavourableStatus(DrMemberFavourable drMemberFavourable);
	

	/**
	 * 查询优惠券信息
	 * @param id 优惠券主键ID
	 * @return
	 */
	public DrMemberFavourable selectByPrimaryKey(Integer id);
	

	/**
	 * 修改优惠券状态
	 * @param drMemberFavourable
	 * @throws SQLException
	 */
	void updateByPrimaryKey(DrMemberFavourable drMemberFavourable) throws SQLException;

	/**
	 * 获取体验金金额合计
	 * @param map
	 * @return
	 */
	public BigDecimal getExperienceAmount(Map<String,Object> map);

	/**
	 *
	 * @param map
	 */
	public void updateByExperience(Map<String,Object> map);

	public Map<String, Object> getFavourableById(Map<String, Object> map);

	/**
	 * 根据uid获取用户优惠卷金额
	 * @param uid
	 * @return
	 */
	public BigDecimal selectHasRedPacket(Integer uid);
}
