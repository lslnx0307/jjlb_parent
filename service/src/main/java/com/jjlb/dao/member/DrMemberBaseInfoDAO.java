package com.jjlb.dao.member;

import com.jjlb.model.entity.member.DrMemberBaseInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author lslnx0307
 */
@Repository
public interface DrMemberBaseInfoDAO {
	
	/**
	 * 根据id查询
	 * @param uid
	 * @return DrMemberBaseInfo
	 */
    public DrMemberBaseInfo queryMemberBaseInfoByUid(int uid); 
    

    /**
	 * 根据（Id，uid，cid）修改会员基本信息
	 * @param drMemberBaseInfo
	 * @return void
	 * @throws SQLException
	 */			
    public void updateDrMemberBaseInfoById(DrMemberBaseInfo drMemberBaseInfo) throws SQLException; 
    

    /**
     * 根据条件查询总数
     * @param map<String,Object>
     * @return Integer
     * @throws SQLException
     */
    public Integer queryMemberBaseInfoCountByMap(Map<String, Object> map) throws SQLException;
}