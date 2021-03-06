package com.jjlb.jzh;

import com.alibaba.fastjson.JSONObject;
import com.jjlb.common.BaseResult;
import com.jjlb.common.SerializeUtil;
import com.jjlb.common.Utils;
import com.jjlb.jzh.data.*;
import com.jjlb.jzh.service.JZHService;
import com.jjlb.jzh.util.ConfigReader;
import com.jjlb.jzh.util.SecurityUtils;
import com.jjlb.jzh.util.StringUtil;
import com.jjlb.model.dto.fuiou.FastRechageDto;
import com.jjlb.model.dto.fuiou.FreeDto;
import com.jjlb.model.dto.fuiou.OnlineBankingRechargeDto;
import com.jjlb.model.dto.member.DrMemberBankDto;
import com.jjlb.model.entity.fuiou.SysFuiouNoticeLog;
import com.jjlb.service.redis.RedisClientTemplate;
import com.jjlb.service.redis.SysFuiouNoticeLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
/**
 * 恒丰存管2.0
 * @author Ernes
 *
 */
public class FuiouConfig {
	
	public final static String MCHNT_CD;//商户代码
	public final static String VER;//版本
	public final static String LOGIN_ID;//登录id
	public final static String OPENACCOUNT_BACK_NOTIFY_URL;//商户异步地址
	public final static String OPENACCOUNT_PAGE_NOTIFY_URL;//商户同步通知地址

	
	public final static String PREAUTHURL;//预授权接口地址（直连）
	public final static String PREAUTHCANCELURL;//撤销预授权接口地址（直连）
	public final static String REGURL;//个人用户自助开户注册（网页版）
	public final static String APPWEBREGURL;//APP个人用户自助开户注册
	public final static String APPSIGN_CARDURL;//APP免登签约
	public final static String APPQRECHARGEURL;//商户APP个人用户免登录快速充值
	public final static String APPQRECHARGE2URL;//商户APP个人用户免登录快捷充值
	public final static String APPWITHDRAWURL;//商户APP个人用户免登录提现
	public final static String APPRESETPASSWORDURL;//用户密码修改重置免登陆接口(app版)
	public final static String BRECHARGEURL;//商户P2P网站免登录网银充值（网页版）
	public final static String MPAY_SENDSMSURL;//快捷充值短信直连
	public final static String MPAY_SENDPAYURL;//快捷充值
	public final static String FREEZEURL;//冻结
	public final static String UNFREEZEURL;//解冻
	public final static String BRECHARGE12URL;//商户P2P网站免登录网银充值跳银行（网页版）
	public final static String BALANCEACTIONURL;//余额查询
	public final static String TRANSFERBUURL;//划拨(个人与个人之间)接口地址

	public final static String REG = "000000";//开户注册（直连）
	public final static String ARTIFREG = "000001";//法人开户注册（直连）
	public final static String PREAUTH = "000002";//预授权（直连）
	public final static String PREAUTHCANCEL = "000003";//撤销预授权（直连）
	public final static String TRANSFERBMU = "000004";//转账（商户与个人之）（直连）
	public final static String TRANSFERBU = "000005";//划拨(个人与个人之间)（直连）
	public final static String FREEZE = "000006";//冻结（直连）
	public final static String TRANSFERBMUANDFREEZE = "000007";//转账预冻结（直连）
	public final static String TRANSFERBUANDFREEZE = "000008";//划拨预冻结（直连）
	public final static String TRANSFERBUANDFREEZE2FREEZE = "000009";//冻结到冻结接口（直连）
	public final static String UNFREEZE = "000010";//解冻（直连）
	public final static String USERCHANGECARD = "000011";//用户更换银行卡接口（直连）
	public final static String WEBREG = "000012";//个人用户自助开户注册（网页版）
	public final static String WEBARTIFREG = "000013";//法人用户自助开户注册（网页版）
	public final static String QRECHARGE = "000014";//商户P2P网站免登录快速充值（网页版）
	public final static String BRECHARGE = "000015";//商户P2P网站免登录网银充值（网页版）
	public final static String WITHDRAW = "000016";//商户P2P网站免登录提现接口（网页版）
	public final static String RESETPASSWORD = "000017";//用户密码修改重置免登陆接口(网页版)
	public final static String PCQRECHARGE500405 = "000018";//PC端个人用户免登录快捷充值(网页版)
	public final static String APPWEBREG = "000019";//APP个人用户自助开户注册
	public final static String APPSIGN_CARD = "000020";//APP免登签约
	public final static String APPQRECHARGE = "000021";//商户APP个人用户免登录快速充值
	public final static String APPQRECHARGE2 = "000022";//商户APP个人用户免登录快捷充值
	public final static String APPWITHDRAW = "000023";//商户APP个人用户免登录提现
	public final static String APPRESETPASSWORD = "000024";//用户密码修改重置免登陆接口(app版)
	public final static String MPAY_SENDSMS = "000025";//快捷充值短信
	public final static String MPAY_SENDPAY = "000026";//快捷充值短信与充值
	public final static String QUERYCZTXURL;//充值提现查询接口
	public final static String TRANSFERBMUURL;//转账（商户与个人之）
	public final static String BRECHARGE12 = "000115";//网银跳银行
	
	
	@Autowired
	private SysFuiouNoticeLogService sysFuiouNoticeLogService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	private static FuiouConfig fuiouConfig;
	public FuiouConfig(){
		
	}
	public void setSysFuiouNoticeLogService(SysFuiouNoticeLogService sysFuiouNoticeLogService) {
        this.sysFuiouNoticeLogService = sysFuiouNoticeLogService;  
    }
	public void setRedisClientTemplate(RedisClientTemplate redisClientTemplate) {
		this.redisClientTemplate = redisClientTemplate;
	}
	//类似声明了，当你加载一个类的构造函数之后执行的代码块，也就是在加载了构造函数之后，就将service复制给一个静态的service
	@PostConstruct  
    public void init() {  
		fuiouConfig = this;
		fuiouConfig.sysFuiouNoticeLogService = this.sysFuiouNoticeLogService; 
		fuiouConfig.redisClientTemplate = this.redisClientTemplate;
    }  
	static { ////注意 properties 里统一小写
		MCHNT_CD = ConfigReader.getConfig("mchnt_cd");
		VER = ConfigReader.getConfig("ver");
		LOGIN_ID = ConfigReader.getConfig("login_id");
		OPENACCOUNT_BACK_NOTIFY_URL = ConfigReader.getConfig("openaccount_back_notify_url");
		OPENACCOUNT_PAGE_NOTIFY_URL =  ConfigReader.getConfig("openaccount_page_notify_url_app");
		PREAUTHURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("preauthurl");
		PREAUTHCANCELURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("preauthcancelurl");
		REGURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("regurl");
		APPWEBREGURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("appwebregurl");
		APPSIGN_CARDURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("appsign_cardurl");
		APPQRECHARGEURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("app500001url");
		APPQRECHARGE2URL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("app500002url");
		APPWITHDRAWURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("app500003url");
		APPRESETPASSWORDURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("appresetpasswordurl");
		BRECHARGEURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("500002url");
		MPAY_SENDSMSURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("mpay_sendsmsurl");
		MPAY_SENDPAYURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("mpay_sendpayurl");
		FREEZEURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("freezeurl");
		UNFREEZEURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("unfreezeurl");
		QUERYCZTXURL=ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("querycztxurl");
		TRANSFERBMUURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("transferbmuurl");
		BRECHARGE12URL =  ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("500012url");
		BALANCEACTIONURL =ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("balanceactionurl");
		TRANSFERBUURL = ConfigReader.getConfig("jzh_url") + ConfigReader.getConfig("transferbuurl");
	}
	
	
	/**
	 * 解冻接口
	 * @param dto
	 * @return
	 */
	public static BaseResult freezeCancel(FreeDto dto){

		BaseResult br = new BaseResult();
		try {
			FreezeReqData data = new FreezeReqData();
			data.setVer(VER);
			data.setMchnt_cd(MCHNT_CD);
			data.setMchnt_txn_ssn(Utils.createOrderNo(6, dto.getUid(), ""));
			data.setCust_no(dto.getCust_no());
			data.setAmt(yuanToCent(dto.getAmt()));
			data.setRem(dto.getRem());
			//记录日志
			SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
			sysFuiouNoticeLog.setIcd(UNFREEZE);
			sysFuiouNoticeLog.setIcd_name("解冻结接口（直连）");
			sysFuiouNoticeLog.setUser_id(data.getCust_no());
			sysFuiouNoticeLog.setAmt(dto.getAmt());
			sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
			JSONObject jsonObject = JZHService.sendHttp(UNFREEZEURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
			br.setMap(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	
	/**
	 *冻结
	 * @param dto
	 * @return
	 */
	public static BaseResult freeze(FreeDto dto){
		BaseResult br = new BaseResult();
		FreezeReqData data = new FreezeReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, dto.getUid(), ""));
		data.setCust_no(dto.getCust_no());
		data.setAmt(yuanToCent(dto.getAmt()));
		data.setRem(dto.getRem());
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(FREEZE);
		sysFuiouNoticeLog.setIcd_name("冻结接口（直连）");
		sysFuiouNoticeLog.setUser_id(data.getCust_no());
		sysFuiouNoticeLog.setAmt(dto.getAmt());
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(FREEZEURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());		
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);	
			br.setMap(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setMsg(re.getMessage());
		} catch (Exception e) {
			br.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	
	/**
	 * 支付密码修改(页面)
	 * @param map
	 * @return
	 */
	public static String resetPassword(Map<String,Object> map){
		ResetPassWordReqData data = new ResetPassWordReqData();
		
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, Integer.parseInt(map.get("id").toString()), ""));
		//1:重置登录密码,2:修改登录密码,3:支付密码重置
		data.setBusi_tp(map.get("busi_tp").toString());
		data.setLogin_id(map.get("login_id").toString());
		
		
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(APPRESETPASSWORD);
		sysFuiouNoticeLog.setUser_id(data.getLogin_id());
		sysFuiouNoticeLog.setIcd_name("支付密码修改(页面)");
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		
		String result = sysFuiouNoticeLog.getReq_message();
		JSONObject obj = JSONObject.parseObject(result);
		obj.getJSONObject("message").put("back_url", OPENACCOUNT_PAGE_NOTIFY_URL+"?wap="+map.get("channel"));
		result = obj.toString();
		
		return result;
		
	}
	
	/**
	 * 充值
	 * @param map fuiou_acnt amt user_id
	 * @param
	 * @param
	 * @return
	 */
	public static String rechargeFirst(Map<String,Object> map){
		RechargeReqData data = new RechargeReqData();
		String amt = yuanToCent(map.get("amt").toString());
		
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn").toString());
		data.setLogin_id(map.get("login_id").toString());
		data.setAmt(amt);
		
		data.setPage_notify_url(FuiouConfig.OPENACCOUNT_PAGE_NOTIFY_URL+"?wap="+map.get("channel"));
		data.setBack_notify_url(FuiouConfig.OPENACCOUNT_BACK_NOTIFY_URL);
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(map.get("icd").toString());
		sysFuiouNoticeLog.setAmt(map.get("amt").toString());
		sysFuiouNoticeLog.setUser_id(data.getLogin_id());
		
		if(PCQRECHARGE500405.equals(map.get("icd").toString())){
			sysFuiouNoticeLog.setIcd_name("快捷充值pc（页面）");
		}else {
			sysFuiouNoticeLog.setIcd_name("快捷充值p2p（页面）");
		}
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		
		return sysFuiouNoticeLog.getReq_message();
	}
	
	/**
	 * 开户
	 * @param mobile_no
	 * @param cust_nm
	 * @param certif_id
	 * @return
	 */
	public static String webReg(Map<String,Object> map){
		
		WebReqData data = new WebReqData();
		data.setVer(FuiouConfig.VER);
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, Integer.parseInt(map.get("id").toString()), ""));
		data.setMobile_no(map.get("mobile_no").toString());
		data.setCust_nm(map.get("cust_nm").toString());
		data.setCertif_id(map.get("certif_id").toString());
//		data.setCertif_tp("0");//证件类型
		data.setPage_notify_url(FuiouConfig.OPENACCOUNT_PAGE_NOTIFY_URL+"?wap="+map.get("channel"));
		data.setBack_notify_url(FuiouConfig.OPENACCOUNT_BACK_NOTIFY_URL);
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(APPWEBREG);
		sysFuiouNoticeLog.setUser_id(data.getMobile_no());
		sysFuiouNoticeLog.setIcd_name("个人开户app（页面）");
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
	
		return sysFuiouNoticeLog.getReq_message();
	}
	/**
	 * 预授权接口
	 * @param params
	 * @return
	 */
	public static BaseResult preAuth(Map<String, Object>params){
		BaseResult br = new BaseResult();
		PreAuthReqData data = new PreAuthReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, (Integer)params.get("uid"), ""));
		data.setOut_cust_no((String)params.get("memberPhone"));
		data.setIn_cust_no((String)params.get("loanPhones"));
		data.setAmt(yuanToCent(params.get("amt").toString()));
		data.setRem((String)params.get("rem"));
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(PREAUTH);
		sysFuiouNoticeLog.setIcd_name("预授权接口（直连）");
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(PREAUTHURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setContract_no(jsonObject.getString("contract_no"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			sysFuiouNoticeLog.setUser_id(data.getOut_cust_no());
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);	
			br.setObj(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	/**
	 * 撤销预授权接口
	 * @param params
	 * @return
	 */
	public static BaseResult preAuthCancel(Map<String, Object>params){
		BaseResult br = new BaseResult();
		PreAuthCancelReqData data = new PreAuthCancelReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, (Integer)params.get("uid"), ""));
		data.setOut_cust_no((String)params.get("memberPhone"));
		data.setIn_cust_no((String)params.get("loanPhones"));
		data.setRem((String)params.get("rem"));
		data.setContract_no((String)params.get("contract_no"));
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(PREAUTHCANCEL);
		sysFuiouNoticeLog.setIcd_name("撤销预授权接口（直连）");
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(PREAUTHCANCELURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			sysFuiouNoticeLog.setUser_id(data.getIn_cust_no());
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);	
			br.setObj(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}	
	
	/**
	 * 提现
	 * @param login_id 用户手机号
	 * @param paymentNum 流水号
	 * @param amount 提现金额 
	 * * @param channel app/wap
	 * @return
	 */
	public static String withdrawals(String login_id,String paymentNum,String amount,String channel){		
		WithdrawalsReqData data = new WithdrawalsReqData();
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(paymentNum);//流水号
		data.setLogin_id(login_id);//登录ID
		data.setAmt(yuanToCent(amount));//金额
		
		data.setPage_notify_url(FuiouConfig.OPENACCOUNT_PAGE_NOTIFY_URL+"?wap="+channel);
		data.setBack_notify_url(FuiouConfig.OPENACCOUNT_BACK_NOTIFY_URL);

		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(APPWITHDRAW);
		sysFuiouNoticeLog.setUser_id(data.getLogin_id());
		sysFuiouNoticeLog.setAmt(amount);
		sysFuiouNoticeLog.setIcd_name("提现（页面）");
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);		
		return sysFuiouNoticeLog.getReq_message();
	}
	
	/**
	 * 分转元
	 * @param amount
	 * @return
	 */
	public static BigDecimal centToYuan(String amount){
		return new BigDecimal(amount).divide(new BigDecimal("100"));
	}
	/**
	 * 元转分
	 * @param amount
	 * @return
	 */
	public static String yuanToCent(String amount){
		return new BigDecimal(amount).multiply(new BigDecimal("100")).intValue()+"";
	}
	/**
	 * 是否授权
	 * @param auth_st 授权状态码
	 * @param type 1投资 2还款
	 * @return
	 */
	public static boolean  isAuth(String auth_st,int type){
		
		if(auth_st != null && !"".equals(auth_st)){
			if(type == 1){
				auth_st = auth_st.substring(0,1);
			}else if(type ==2){
				auth_st = auth_st.substring(1,2);
			}
			if("1".equals(auth_st)) {
				return true;
			}
		}else{
			return false;
		}
		return false;
	}
	
	
	/**
	 * 商户响应通知
	 */
	public static String  resNotice(String mchnt_txn_ssn,String resp_code){
		
		BaseRspdata data = new BaseRspdata();
		data.setResp_code(resp_code);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(mchnt_txn_ssn);
		
		String message = StringUtil.encaJSONstring(data);
		System.out.println("存管商户返回通知:"+message);
		return message;
	}
	
	/**
	 * 开户直连
	 * @param dto
	 * @return
	 */
	public static BaseResult reg(DrMemberBankDto dto){
		BaseResult br = new BaseResult();
		RegData data = new RegData();

		BeanUtils.copyProperties(dto,data);
		data.setVer(VER);
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);

		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(REG);
		sysFuiouNoticeLog.setIcd_name("开户直连");
		sysFuiouNoticeLog.setUser_id(data.getMobile_no());
		sysFuiouNoticeLog.setChannel(Integer.valueOf(dto.getChannel()));
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(REGURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
			br.setMap(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	
	/**
	 * 获取短信验证码
	 * @param fuiou_acnt
	 * @param amt
	 * @param user_id
	 * @return
	 */
	public static BaseResult sendSms(Map<String,String> map){
		BaseResult br = new BaseResult();
		SendSmsData data = new SendSmsData();
		
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn")); //                
		data.setLogin_id(map.get("login_id")); //M交易用户           
		data.setAmt(yuanToCent(map.get("amt"))); //M交易金额           
		data.setBank_mobile(map.get("bank_mobile")); //O银行预留手机号    
//		data.setCard_no(map.get("card_no")); //O银行卡号     
		
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setMchnt_txn_ssn("R"+sysFuiouNoticeLog.getMchnt_txn_ssn());//日志表里流水号唯一,充值与短信流水是同一个所以加R
		sysFuiouNoticeLog.setIcd(MPAY_SENDSMS);
		sysFuiouNoticeLog.setIcd_name("充值短信验证码");
		sysFuiouNoticeLog.setUser_id(data.getLogin_id());
		sysFuiouNoticeLog.setChannel(Integer.valueOf(map.get("channel")));
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(MPAY_SENDSMSURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);	
			br.setObj(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	
	
	/**
	 * 充值直连
	 * @param dto
	 * @return
	 */
	public static BaseResult fastRecharg(FastRechageDto dto){
		BaseResult br = new BaseResult();
		FastRecharg data = new FastRecharg();
		
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		//该流水必须与获取交易银行卡预留手机短信验证码时，上送的商户流水保持一致
		data.setMchnt_txn_ssn(dto.getMchnt_txn_ssn());
		//M交易用户
		data.setTxn_date(dto.getTxn_date());
		//M交易金额
		data.setYzm(dto.getYzm());

		
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(MPAY_SENDPAY);
		sysFuiouNoticeLog.setIcd_name("快捷充值");
		sysFuiouNoticeLog.setUser_id(dto.getLogin_id());
		sysFuiouNoticeLog.setChannel(dto.getChannel());
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		try {
			JSONObject jsonObject = JZHService.sendHttp(MPAY_SENDPAYURL, data);
			//修改日志日志
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setResp_code(jsonObject.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(jsonObject.getString("resp_desc"));
			sysFuiouNoticeLog.setMessage(jsonObject.toString());
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);	
			br.setObj(jsonObject);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
	/**
	 * 金账户交易通知返回XML
	 * @param resp_code
	 * @param mchnt_cd
	 * @param mchnt_txn_ssn
	 * @return
	 * @throws Exception 
	 */
	public static String notifyRspXml(String resp_code,String mchnt_txn_ssn) {
	
		String plain = "<plain>";
		plain +="<resp_code>"+resp_code+"</resp_code>";
		plain +="<mchnt_cd>"+MCHNT_CD+"</mchnt_cd>";
		plain +="<mchnt_txn_ssn>"+mchnt_txn_ssn+"</mchnt_txn_ssn>";
		plain +="</plain>";
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<ap>");
		sb.append(plain);
		sb.append("<signature>"+ SecurityUtils.sign(plain)+"</signature>");
		sb.append("</ap>");
		return sb.toString();
	}
	//日志队列
	public static void incrMg(SysFuiouNoticeLog fuiouNoticeLog) {
		fuiouConfig.redisClientTemplate.lpush("fuiouMessageList".getBytes(), SerializeUtil.serialize(fuiouNoticeLog));
	}
	/** 充值提现查询
	 * @param map
	 * @return
	 */
	public static BaseResult QueryCzTx(Map<String, String> map){
		BaseResult br = new BaseResult();
		QueryCzTxReqData data=new QueryCzTxReqData();
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn"));//m
		data.setVer(VER);
		data.setTxn_ssn(map.get("txn_ssn"));// m -交易流水
		data.setBusi_tp(map.get("busi_tp"));//m -PW11 充值PWTX 提现PWTP 退票
		data.setStart_time(map.get("start_time"));//m
		data.setEnd_time(map.get("end_time"));//m
		data.setTxn_st(map.get("txn_st"));//o-1 交易成功2 交易失败
		data.setCust_no(map.get("cust_no"));//o-个人 
		data.setPage_no(map.get("page_no"));//o-大于零的整数；默认为1;
		data.setPage_size(map.get("page_size"));//o-[10,100]之间整数; 默认值:10; 最大值:100;
	
		try {
			JSONObject json = JZHService.sendHttp(QUERYCZTXURL,data);
			br.setObj(json);
			if(!"0000".equals((String)json.get("resp_code"))){
				br.setSuccess(false);
				br.setMsg((String)json.get("resp_desc"));
				br.setObj(json);
				return br;
			}else{			
				if(json.containsKey("total_number") && !"0".equals(json.get("total_number"))){
					JSONObject result = json.getJSONObject("results").getJSONObject("result");
					br.setMsg( result.get("txn_rsp_cd")+"|"+result.get("rsp_cd_desc"));
				}else{
					br.setMsg("定单不存在或交易失败");
				}
			}
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
		
	}
	
	/**
	 * 转账
	 * @param map
	 * @return
	 */
	public static BaseResult transferBmu(Map<String, Object> map){
		BaseResult br = new BaseResult();
		TransferBmuReqData data = new TransferBmuReqData();
		data.setVer(FuiouConfig.VER);
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(map.get("mchnt_txn_ssn").toString());
		data.setOut_cust_no(map.get("out_cust_no").toString());
		data.setIn_cust_no(map.get("in_cust_no").toString());
		data.setAmt(yuanToCent(map.get("amt").toString()));
		data.setRem((String)map.get("rem"));
		data.setContract_no(map.get("contract_no").toString());
		
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(TRANSFERBMU);
		if(map.get("icd_name").toString().equals("平台提现手续费")){
			sysFuiouNoticeLog.setUser_id(data.getOut_cust_no());
		}else{
			sysFuiouNoticeLog.setUser_id(data.getIn_cust_no());
		}
		sysFuiouNoticeLog.setAmt(data.getAmt());
		sysFuiouNoticeLog.setRemark((String)map.get("rem"));		
		sysFuiouNoticeLog.setIcd_name(map.get("icd_name").toString());//划拨类型
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
				
		try {
			JSONObject json = JZHService.sendHttp(TRANSFERBMUURL,data);
			
			//修改日志
			sysFuiouNoticeLog.setMessage(json.toString());
			sysFuiouNoticeLog.setResp_code(json.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(json.getString("resp_desc"));
			sysFuiouNoticeLog.setStatus(2);
			if(!"0000".equals((String)json.get("resp_code"))){
				br.setSuccess(false);
				br.setMsg((String)json.get("resp_desc"));
				br.setObj(json);
				sysFuiouNoticeLog.setStatus(3);
				sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
				return br;
			}
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
			br.setObj(json);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
		
	}

	/**
	 * 网银充值跳银行
	 * @param dto
	 * @return
	 */
	public static String onlineBankingRecharge12Data(OnlineBankingRechargeDto dto){
		FuiouOnlineBanking12Req data = new FuiouOnlineBanking12Req();
		String amt = yuanToCent(dto.getAmt());
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(dto.getMchnt_txn_ssn());
		data.setAmt(amt);
		data.setIss_ins_cd(dto.getIss_ins_cd());
		data.setOrder_pay_type(dto.getOrder_pay_type());

		data.setLogin_id(dto.getLogin_id());
		data.setPage_notify_url(FuiouConfig.OPENACCOUNT_PAGE_NOTIFY_URL);
		data.setBack_notify_url(FuiouConfig.OPENACCOUNT_BACK_NOTIFY_URL);
		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(BRECHARGE12);
		sysFuiouNoticeLog.setAmt(dto.getAmt());
		sysFuiouNoticeLog.setUser_id(data.getLogin_id());
		sysFuiouNoticeLog.setIcd_name("网银充值（页面）");
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);
		return sysFuiouNoticeLog.getReq_message();
	}

	/**
	 * 查询余额
	 * @param map
	 * @return
	 */
	public static BaseResult balanceAction(Map<String, String> map){
		BaseResult br = new BaseResult();
		BalanceActionReqData data = new BalanceActionReqData();
		data.setMchnt_cd(FuiouConfig.MCHNT_CD);
		data.setMchnt_txn_ssn(Utils.createOrderNo(6, 1, ""));
		Date newDate=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		data.setMchnt_txn_dt(sdf.format(newDate));
		data.setCust_no(map.get("cust_no").toString());

		try {
			JSONObject json = JZHService.sendHttp(BALANCEACTIONURL,data);
			if(!"0000".equals((String)json.get("resp_code"))){
				br.setSuccess(false);
				br.setMsg((String)json.get("resp_desc"));
				br.setMap(json);
				return br;
			}
			br.setMap(json);
			br.setSuccess(true);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;

	}

	/**
	 * 划拨
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static BaseResult transferBu(Map<String, String>params){
		BaseResult br = new BaseResult();
		br.setSuccess(true);
		TransferBuReqData data = new TransferBuReqData();
		data.setVer(VER);
		data.setMchnt_cd(MCHNT_CD);
		data.setMchnt_txn_ssn(params.get("mchnt_txn_ssn"));

		//付款账户
		data.setOut_cust_no(params.get("out_cust_no"));
		//收款账户
		data.setIn_cust_no(params.get("in_cust_no"));
		//金额 分为单位
		data.setAmt(yuanToCent(params.get("amt")));
		data.setRem(params.get("rem"));
		//预授权合同号
		data.setContract_no(params.get("contract_no"));

		//记录日志
		SysFuiouNoticeLog sysFuiouNoticeLog = new SysFuiouNoticeLog(data);
		sysFuiouNoticeLog.setIcd(TRANSFERBU);
		sysFuiouNoticeLog.setUser_id(data.getOut_cust_no());
		sysFuiouNoticeLog.setAmt(data.getAmt());
		//划拨类型
		sysFuiouNoticeLog.setIcd_name("划拨直连");
		sysFuiouNoticeLog.setMgType(1);incrMg(sysFuiouNoticeLog);

		try {
			JSONObject json = JZHService.sendHttp(TRANSFERBUURL,data);
			//修改日志日志
			sysFuiouNoticeLog.setMessage(json.toString());
			sysFuiouNoticeLog.setResp_code(json.getString("resp_code"));
			sysFuiouNoticeLog.setResp_desc(json.getString("resp_desc"));
			//3122原授权交易已完成
			if(!"0000".equals((String)json.get("resp_code")) && !"3122".equals((String)json.get("resp_code")) ){
				br.setSuccess(false);
				br.setMsg((String)json.get("resp_desc"));
				br.setMap(json);
				sysFuiouNoticeLog.setStatus(3);
				sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
				return br;
			}
			sysFuiouNoticeLog.setStatus(2);
			sysFuiouNoticeLog.setMgType(2);incrMg(sysFuiouNoticeLog);
			br.setMap(json);
		} catch(RuntimeException re){
			br.setSuccess(false);
			br.setMsg(re.getMessage());
		} catch (Exception e) {
			br.setSuccess(false);
			br.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return br;
	}
}
