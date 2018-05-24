package com.jjlb.model.entity.product;

import java.math.BigDecimal;
import java.util.Date;

public class JsProductPrizeLog {
	private Integer id;
	private Integer uid;//用户id
	private Integer investId;//投资id
	private Integer ppid;//奖品id
	private Date addTime;
	private Integer type;//定单类型,0投资定单,1预约定单  
	
	
	private BigDecimal amount;//投资金额
	private String prizeName;//奖品名称
	private BigDecimal price;//原价
	private String h5ImgUrlV;//h5奖品图片-竖版
	private Integer prizeType;//奖品类型，1虚拟0实物
	private String name;//联系人姓名
	private String phone;//联系人手机
	private String address;//联系人地址
	private Date investTime;//投资时间

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getH5ImgUrlV() {
		return h5ImgUrlV;
	}

	public void setH5ImgUrlV(String h5ImgUrlV) {
		this.h5ImgUrlV = h5ImgUrlV;
	}

	public Date getInvestTime() {
		return investTime;
	}

	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}

	public Integer getPrizeType() {
		return prizeType;
	}
	public void setPrizeType(Integer prizeType) {
		this.prizeType = prizeType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public JsProductPrizeLog() {
		super();
	}
	public JsProductPrizeLog(Integer id, Integer uid, Integer investId,
			Integer ppid, Date addTime, Integer type) {
		super();
		this.id = id;
		this.uid = uid;
		this.investId = investId;
		this.ppid = ppid;
		this.addTime = addTime;
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getInvestId() {
		return investId;
	}
	public void setInvestId(Integer investId) {
		this.investId = investId;
	}
	public Integer getPpid() {
		return ppid;
	}
	public void setPpid(Integer ppid) {
		this.ppid = ppid;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}