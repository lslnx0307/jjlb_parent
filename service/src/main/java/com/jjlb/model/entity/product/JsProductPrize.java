package com.jjlb.model.entity.product;

import java.math.BigDecimal;
import java.util.Date;

public class JsProductPrize {
	private Integer id;
	private String name;//全称
	private String simpleName;//简称
	private BigDecimal price;//原价
	private BigDecimal amount;//投资金额
	private Integer sort;//权重
	private Integer status;//状态0未上架,1上架,2下架   
	private String pcImgUrlV;//pc奖品图片-竖版   
	private String pcImgUrlH;//pc奖品图片-横版    
	private String pcDetailImgUrl;//pc奖品详情-横版    
	private String h5ImgUrlV;//h5奖品图片-竖版    
	private String h5ImgUrlH;//h5奖品图片-横版    
	private String h5DetailImgUrl;//h5奖品详情图片-横版    
	private Date addTime;//添加时间
	private Integer addUser;//添加人key   
	private Date updateTime;//修改时间
	private Integer updateUser;//修改人key  
	private Integer pid; //产品id
	private Integer isNot; //是否抢完  1 抢完
	private BigDecimal rate;
	private BigDecimal activityRate;
	private Integer deadline; 
	private Integer prizeType;//奖品类型
	private String investSendUrl; //投即送跳转链接
	private String investSendDetailUrl;//投即送奖品详情链接
	private Integer category;  //1:夏日专享2:品质生活3:数码优选'
	
	
	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getInvestSendDetailUrl() {
		return investSendDetailUrl;
	}

	public void setInvestSendDetailUrl(String investSendDetailUrl) {
		this.investSendDetailUrl = investSendDetailUrl;
	}

	public String getInvestSendUrl() {
		return investSendUrl;
	}

	public void setInvestSendUrl(String investSendUrl) {
		this.investSendUrl = investSendUrl;
	}

	public Integer getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(Integer prizeType) {
		this.prizeType = prizeType;
	}

	public JsProductPrize() {
		super();
	}
	
	public JsProductPrize(Integer id, String name, String simpleName,
			BigDecimal price, BigDecimal amount, Integer sort, Integer status,
			String pcImgUrlV, String pcImgUrlH, String h5ImgUrlV,
			String h5ImgUrlH, Date addTime, Integer addUser, Date updateTime,
			Integer updateUser) {
		super();
		this.id = id;
		this.name = name;
		this.simpleName = simpleName;
		this.price = price;
		this.amount = amount;
		this.sort = sort;
		this.status = status;
		this.pcImgUrlV = pcImgUrlV;
		this.pcImgUrlH = pcImgUrlH;
		this.h5ImgUrlV = h5ImgUrlV;
		this.h5ImgUrlH = h5ImgUrlH;
		this.addTime = addTime;
		this.addUser = addUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPcImgUrlV() {
		return pcImgUrlV;
	}
	public void setPcImgUrlV(String pcImgUrlV) {
		this.pcImgUrlV = pcImgUrlV;
	}
	public String getPcImgUrlH() {
		return pcImgUrlH;
	}
	public void setPcImgUrlH(String pcImgUrlH) {
		this.pcImgUrlH = pcImgUrlH;
	}
	public String getH5ImgUrlV() {
		return h5ImgUrlV;
	}
	public void setH5ImgUrlV(String h5ImgUrlV) {
		this.h5ImgUrlV = h5ImgUrlV;
	}
	public String getH5ImgUrlH() {
		return h5ImgUrlH;
	}
	public void setH5ImgUrlH(String h5ImgUrlH) {
		this.h5ImgUrlH = h5ImgUrlH;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Integer getAddUser() {
		return addUser;
	}
	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getIsNot() {
		return isNot;
	}

	public void setIsNot(Integer isNot) {
		this.isNot = isNot;
	}

	public String getPcDetailImgUrl() {
		return pcDetailImgUrl;
	}

	public void setPcDetailImgUrl(String pcDetailImgUrl) {
		this.pcDetailImgUrl = pcDetailImgUrl;
	}

	public String getH5DetailImgUrl() {
		return h5DetailImgUrl;
	}

	public void setH5DetailImgUrl(String h5DetailImgUrl) {
		this.h5DetailImgUrl = h5DetailImgUrl;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	public BigDecimal getActivityRate() {
		return activityRate;
	}

	public void setActivityRate(BigDecimal activityRate) {
		this.activityRate = activityRate;
	}
	
	
}
