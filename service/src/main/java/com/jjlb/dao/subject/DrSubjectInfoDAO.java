package com.jjlb.dao.subject;

import com.jjlb.model.entity.subject.DrSubjectInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * @author lslnx0307
 */
@Repository
public interface DrSubjectInfoDAO {
	

	/**
	 * 根据id得到标的信息 拿到的金额和剩余金额单位是万元
	 * @param id
	 * @return DrSubjectInfo
	 */
    public DrSubjectInfo getDrSubjectInfoByid(Integer id); 
    

 	/**
 	 * 修改标的信息
 	 * @param  drSubjectInfo
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void updateDrSubjectInfo(DrSubjectInfo drSubjectInfo) throws SQLException; 
    

}