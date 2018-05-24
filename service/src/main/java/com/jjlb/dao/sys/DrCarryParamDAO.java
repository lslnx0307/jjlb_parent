package com.jjlb.dao.sys;

import com.jjlb.model.entity.sys.DrCarryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrCarryParamDAO {
	
	/**
	 * 拿到提现设置信息
	 * @return DrCarryParam
	 */
    public DrCarryParam getDrCarryParam();
    
	/**
	 * 根据UID判断是否收取手续费 
	 * @param uid
	 * @return 0-不收手续费 1-收手续费
	 */
    public Integer getDrCarryParamIsCharge(Integer uid);
	/**
	 * 得到提现设置信息
	 * @return List<DrCarryParam>
	 */
    public List<DrCarryParam> getDrCarryParamList(); 
    
	
}