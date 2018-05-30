package com.jjlb.dao.member;

import com.jjlb.model.entity.member.DrMemberMsg;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * @author lslnx0307
 *
 */
@Repository
public interface DrMemberMsgDAO {

    /**
	 * @Description 添加
	 * @param drMemberMsg
	 * @return void
	 * @throws SQLException
	 */
    public void insertDrMemberMsg(DrMemberMsg drMemberMsg) throws SQLException;


}