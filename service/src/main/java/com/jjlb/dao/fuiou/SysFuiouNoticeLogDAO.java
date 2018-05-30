package com.jjlb.dao.fuiou;

import com.jjlb.model.entity.fuiou.SysFuiouNoticeLog;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface SysFuiouNoticeLogDAO {

	/**
	 * 插入富友验签记录
	 * @param sysFuiouNoticeLog
	 */
    public void insert(SysFuiouNoticeLog sysFuiouNoticeLog);

    /**
     * 根据流水号查询
     * @param mchnt_txn_ssn
     * @return
     */
    public SysFuiouNoticeLog selectObjectBy_txn_ssn(String mchnt_txn_ssn);

    /**
     * 更新
     * @param sysFuiouNoticeLog
     */
    public void update(SysFuiouNoticeLog sysFuiouNoticeLog);
    /**
     * 更新
     * @param sysFuiouNoticeLog
     */
    public void updateByMchnt_txn_ssn(SysFuiouNoticeLog sysFuiouNoticeLog);
    
    /**
     * selectMembmerOutCustRealname
     * @param mobile
     */
    public String selectMembmerOutCustRealname(String mobile);
    
    /**
     * selectCompanyOutCustRealname
     * @param mobile
     * @return
     */
    public String selectCompanyOutCustRealname(String mobile);
    
    /**
     * insertAbnormalFiling
     * @param map
     */
    public void insertAbnormalFiling(Map<String, Object> map);
}