package com.jjlb.service.redis;

import com.alibaba.fastjson.JSONObject;
import com.jjlb.common.SerializeUtil;
import com.jjlb.jzh.FuiouConfig;
import com.jjlb.model.entity.fuiou.SysFuiouNoticeLog;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageListener implements InitializingBean {
	
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	@Autowired
	private SysFuiouNoticeLogService fnlService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("fuiou_message_log 消息队列 监听启动...111");
		fuiouMessageTread();
	}
	//fuiou_message_log 消息队列 监听启动
	public void fuiouMessageTread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("fuiou_message_log 消息队列 监听启动成功。。。");
				SysFuiouNoticeLog fnl ;
				byte[] bytes = null;
				long millis = 1000l;
				
				while(true){
					try {
						Thread.sleep(millis);
						bytes = redisClientTemplate.rpop("fuiouMessageList".getBytes());
						fnl = (SysFuiouNoticeLog)SerializeUtil.unserialize(bytes);
						
						if(fnl != null ) {
							millis = 500l;
							
							fuiouMessageHandle(fnl);
							
						}else if (millis > 2000l) {// 4秒运行一次
							millis = 4000l;
						}else if (millis > 1000l) {// 2秒运行一次
							millis = 2000l;
						}else {//1秒运行一次
							millis = 1000l;
						}
						fnl = null;
						bytes = null;
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
						if (bytes != null) {
							try{
								System.out.println(JSONObject.toJSON((SysFuiouNoticeLog)SerializeUtil.unserialize(bytes)));
							}catch(Exception a){
								a.printStackTrace();
							}
							redisClientTemplate.lpush("fuiouMessageList_bak".getBytes(), bytes);
						}
					}
				}
			}
		}).start();
		System.out.println("fuiou_message_log 消息队列监听程序启动...");
	}
	// 调用service
	public void fuiouMessageHandle(SysFuiouNoticeLog fnl) {
		if (fnl.getMgType() !=null && fnl.getMgType() ==1) {//添加
			System.out.println("----redis插入fuiou_log表"+"日志类型:"+fnl.getIcd_name()+"===="+fnl.getUser_id()+","+fnl.getMchnt_txn_ssn());
			fnlService.insert(fnl);
		}else if (fnl.getMgType() !=null && fnl.getMgType() == 2) {
			fnlService.updateByMchnt_txn_ssn(fnl);
			
			if (fnl.getResp_code() != null && "0000".equals(fnl.getResp_code())) {
				if (FuiouConfig.FREEZE.equals(fnl.getIcd())) {//
					fnlService.insertAbnormalFiling(fnl, "PWDJ");
				}else if (FuiouConfig.UNFREEZE.equals(fnl.getIcd())){
					fnlService.insertAbnormalFiling(fnl, "PWJD");
				}
			}
			
		}
	}
	
}
