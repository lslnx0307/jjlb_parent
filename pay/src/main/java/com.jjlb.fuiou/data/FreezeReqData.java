package com.jjlb.fuiou.data;

import com.jjlb.fuiou.data.BaseReqdata;

public class FreezeReqData  extends BaseReqdata{

	/**
	 * //M文档版本号
	 */
	private String ver = "";
	/**
	 *  //M冻结目标登录账户
	 */
	private String cust_no = "";
	/**
	 * //M冻结金额
	 */
	private String amt = "";
	/**
	 * //O备注
	 */
	private String rem = "";
	
	
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getCust_no() {
		return cust_no;
	}
	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getRem() {
		return rem;
	}
	public void setRem(String rem) {
		this.rem = rem;
	}
}
