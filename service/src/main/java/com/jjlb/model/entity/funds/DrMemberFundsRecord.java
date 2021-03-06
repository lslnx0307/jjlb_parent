package com.jjlb.model.entity.funds;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lslnx0307
 */
public class DrMemberFundsRecord {
	
	private Integer id;
	
	//投资记录ID
	private Integer investId;
	
	//关联的产品ID
	private Integer pid;
	
	//用户ID
	private Integer uid;
	
	//交易类型
	private Integer tradeType;
	
	//0-支出，1收入
	private Integer type;
	
	//变动金额	
	private BigDecimal amount;
	
	//账户余额
	private BigDecimal balance;
	
	//状态
	private Integer status;
	
	//备注
	private String remark;
	
	//发生时间
	private Date addTime;
	
	//订单编号
	private String orderNo;
	
	public DrMemberFundsRecord(){}

	public DrMemberFundsRecord(Integer pid, Integer investId,Integer uid, Integer tradeType,
			Integer type, BigDecimal amount, BigDecimal balance,
			Integer status, String remark,String orderNo) {
		super();
		this.pid = pid;
		this.investId = investId;
		this.uid = uid;
		this.tradeType = tradeType;
		this.type = type;
		this.amount = amount;
		this.balance = balance;
		this.status = status;
		this.remark = remark;
		this.orderNo = orderNo;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public Integer getInvestId() {
		return investId;
	}


	public void setInvestId(Integer investId) {
		this.investId = investId;
	}

}
