package com.jjlb.dao.product;

import com.jjlb.model.entity.product.DrProductInvest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author lslnx0307
 */
@Repository
public interface DrProductInvestDAO {


	/**
	 *  selectInvestLogCountByParam
	 * @param map
	 * @return
	 */
	public Integer selectInvestLogCountByParam(Map<String, Object> map);
    
    /**
     * 只插入有值得字段
     * @param invest
     */
    public void insertSelective(DrProductInvest invest);
    
    /**
     * 根据map条件计算用户投资次数
     * @param map
     * @return
     */
    public Integer selectInvestCountByMap(Map<String, Object> map);
    
	/**
	 * 根据条件查询是否可提现
	 * @param uid
	 * @return
	 */
	public Integer selectInvestCount(Integer uid);

	/**
	 * 根据产品ID查询未处理的投资记录
	 * @param pid
	 * @return
	 */
	public List<DrProductInvest> getFuiouDrProductInvestListByPid(@Param("pid")Integer pid);

	/**
	 * 通过ID批量修改投资记录状态
	 */
	public void updateStatusByIds(@Param("status")String status,@Param("ids")String[] ids);
}
