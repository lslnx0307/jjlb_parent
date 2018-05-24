package com.jjlb.service.redis.impl;

import com.jjlb.common.SmsSendUtil;
import com.jjlb.common.Utils;
import com.jjlb.dao.sms.SysMessageLogDAO;
import com.jjlb.model.entity.fuiou.SysMessageLog;
import com.jjlb.service.redis.SysMessageLogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * @author lslnx0307
 */
@Service
@Transactional
public class SysMessageLogServiceImpl implements SysMessageLogService {
	private static final Logger log = Logger.getLogger(SysMessageLogServiceImpl.class);

	@Autowired
	public SysMessageLogDAO sysMessageLogDAO;
	
	@Override
	public Integer insert(SysMessageLog sysMessageLog) throws SQLException {
		sysMessageLogDAO.insertSysMessageLog(sysMessageLog);
		return sysMessageLog.getMsgId();
	}

	@Override
	public SysMessageLog selectByProperty(SysMessageLog sysMessageLog) {
		return sysMessageLogDAO.selectByProperty(sysMessageLog);
	}

	@Override
	public Integer sendMsg(SysMessageLog logs,int type) {
		try {
			Integer result = null;
			if(Utils.isObjectNotEmpty(logs.getSendTime())){
				result = SmsSendUtil.sendTimeMsg(logs.getPhone(), logs.getMessage(),Utils.format(logs.getSendTime(), "yyyy-MM-dd HH:mm:ss"));//发送短信
			}else{
//				result = SmsSendUtil.sendMsg(logs.getPhone(), logs.getMessage(),type);
				logs.setSendTime(new Date());
			}
			//记录日志
			logs.setResults(result);
			sysMessageLogDAO.insertSysMessageLog(logs);
			if(result>100){
				result = -1;
			}
			return result;
		} catch (Exception e) {
			log.error("短信发送失败", e);
			e.printStackTrace();
			return -1;
		}
	}
	
	@Override
	public Integer selectMsgLogSendCount(Map<String, Object> map)throws Exception {
		return sysMessageLogDAO.selectMsgLogSendCount(map);
	}

}
