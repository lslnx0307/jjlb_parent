package com.jjlb.model.entity.member;

import java.util.Date;

/**
 * @author lslnx0307
 */
public class DrMemberFuIou {
	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 平台用户id
	 */
	private Integer userId;
	/**
	 * fuiou平台账户
	 */
	private String fuiouId;
	/**
	 * 开户时间
	 */
	private Date addTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFuiouId() {
		return fuiouId;
	}

	public void setFuiouId(String fuiouId) {
		this.fuiouId = fuiouId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
}