package com.jjlb.dao.product;

import com.jjlb.model.subject.DrSubjectInfo;

public interface DrSubjectInfoDAO {
	/**
	 * 根据id得到标的信息 拿到的金额和剩余金额单位是万元
	 * @param id
	 * @return DrSubjectInfo
	 */
    public DrSubjectInfo getDrSubjectInfoByid(Integer id); 
}
