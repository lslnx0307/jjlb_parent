package com.jjlb.dao.sms;

import com.jjlb.model.entity.fuiou.SysMessageLog;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author lslnx0307
 */
@Repository
public interface SysMessageLogDAO {
	
	/**
	 * @Description 插入短信日志
	 * @param sysMessageLog
	 * @return void
	 * @throws SQLException
	 */
    public void insertSysMessageLog(SysMessageLog sysMessageLog) throws SQLException;
    
    
    /**
	 * 根据短信属性查找最后一条记录
	 * @param sysMessageLog
	 * @return
	 */
    public SysMessageLog selectByProperty(SysMessageLog sysMessageLog);
    /**
     * 查询phone当天发送type类型短信多少条
     * @param map
     * @return
     * @throws SQLException
     */
    public int selectMsgLogSendCount(Map<String, Object> map) throws SQLException;
    
}