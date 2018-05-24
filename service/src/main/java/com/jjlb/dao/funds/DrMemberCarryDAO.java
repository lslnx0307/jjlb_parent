package com.jjlb.dao.funds;

import com.jjlb.model.entity.funds.DrMemberCarry;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author lslnx0307
 */
@Repository
public interface DrMemberCarryDAO {
	/**
	 * 添加提现
	 * @param drMemberCarry
	 * @return void
	 * @throws SQLException
	 */
    public void insertDrMemberCarry(DrMemberCarry drMemberCarry) throws SQLException;
    
	/**
	 * 修改状态
	 * @param drMemberCarry
	 * @return void
	 * @throws SQLException
	 */
    public void updateStatusById(DrMemberCarry drMemberCarry) throws SQLException; 
    
    public BigDecimal selectCarryAmountCount(@Param(value = "uid") Integer uid, @Param(value = "type") Integer type);
    
    /**
     * 查询历史提现总额
     * @param uid
     * @return
     */
    public BigDecimal selectAmountByUid(Integer uid); 
    /**
     * 根据uid获取最后一笔提现的订单
     * @param uid
     * @return
     */
    public DrMemberCarry selectCarryByUid(Integer uid);
    /**
	 * 根据商户订单号查询
	 * @param map
	 * @return DrMemberCarry
	 * @throws SQLException
	 */
    public DrMemberCarry selectDrMemberCarryByPaymentnum(Map<String, Object> map);
    
    /**
     * 根据流水号查询提现记录
     * @param paymentnum
     * @return
     */
    public DrMemberCarry selectDrMemberCarryByPaymentnumFuIou(String paymentnum);

}