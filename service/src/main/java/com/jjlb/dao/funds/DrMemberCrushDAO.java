package com.jjlb.dao.funds;

import com.jjlb.model.entity.funds.DrMemberCrush;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * @author lslnx0307
 */
@Repository
public interface DrMemberCrushDAO {
    
	/**
	 * 添加充值记录
	 * @param drMemberCrush
	 * @return void
	 * @throws SQLException
	 */
    public void insertDrMemberCrush(DrMemberCrush drMemberCrush) throws SQLException;
    
	/**
	 * 根据商户唯一订单号查询
	 * @param payNum
	 * @return DrMemberCrush
	 */
    public DrMemberCrush getDrMemberCrushByPayNum(String payNum); 

    
	/**
	 * 根据商户订单号修改
	 * @param drMemberCrush
	 * @return void
	 * @throws SQLException
	 */
    public void updateMemberCrushById(DrMemberCrush drMemberCrush) throws SQLException; 
    
    /**
     * 查询充值总额
     * @param uid
	 * @param type
     * @return
     */
    public BigDecimal getDrMemberCrushAmountByUID(@Param(value = "uid") Integer uid, @Param(value = "type") Integer type);

    
}