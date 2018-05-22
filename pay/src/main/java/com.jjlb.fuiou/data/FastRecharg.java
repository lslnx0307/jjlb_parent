package com.jjlb.fuiou.data;

/**
 * 充值
 * @author DELL
 *
 */
public class FastRecharg extends BaseReqdata{

	/**
	 * //交易发起日期,格式：yyyyMMdd
	 */
	private String txn_date ="";
	/**
	 * //M交易金额
	 */
	private String yzm ="";
	/**
	 * //O银行预留手机号
	 */
	private String bank_mobile="";
	
	public String getTxn_date() {
		return txn_date;
	}
	public void setTxn_date(String txn_date) {
		this.txn_date = txn_date;
	}
	public String getYzm() {
		return yzm;
	}
	public void setYzm(String yzm) {
		this.yzm = yzm;
	}
	public String getBank_mobile() {
		return bank_mobile;
	}
	public void setBank_mobile(String bank_mobile) {
		this.bank_mobile = bank_mobile;
	}
	
	
	
}
