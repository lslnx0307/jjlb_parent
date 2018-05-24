package com.jjlb.service.redis;


import com.jjlb.model.entity.fuiou.SysFuiouNoticeLog;

public interface SysFuiouNoticeLogService {

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
     * 更新
     * @param sysFuiouNoticeLog
     */
    public void insertAbnormalFiling(SysFuiouNoticeLog sysFuiouNoticeLog, String type);
	
}
