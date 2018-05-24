package com.jjlb.dao.bank;

import com.jjlb.model.entity.bank.SysBank;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author lslnx0307
 */
@Repository
public interface SysBankDAO {
    
	/**
	 * 根据map查询银行信息
	 * @param map
	 * @return SysBank
	 */
    public SysBank selectSysBank(Map<String, Object> map);
    
    /**
     * 获取银行单笔和每日限额列表
     * @return
     */
    public List<Map<String,Object>> selectSysBankQuotaList();
    
    
    public String selectBankByCode(@Param("bankCode") String bankCode);
}