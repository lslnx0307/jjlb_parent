package com.jjlb.dao.member;

import com.jjlb.model.entity.member.DrMemberBaseInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public interface DrMemberBaseInfoDAO {
	
	/**
	 * 根据id查询
	 * @param uid
	 * @return DrMemberBaseInfo
	 */
    public DrMemberBaseInfo queryMemberBaseInfoByUid(int uid); 
    
	/**
	 * 根据条件查询
	 * @param map
	 * @return List<DrMemberBaseInfo>
	 * @throws SQLException
	 */
    public List<DrMemberBaseInfo> queryMemberBaseInfoByMap(Map<String, Object> map) throws SQLException;
    
    /**
	 * 根据（Id，uid，cid）修改会员基本信息
	 * @param drMemberBaseInfo
	 * @return void
	 * @throws SQLException
	 */			
    public void updateDrMemberBaseInfoById(DrMemberBaseInfo drMemberBaseInfo) throws SQLException; 
    
    /**
	 * 添加会员基本(住址)资料表
	 * @param drMemberBaseInfo
	 * @return void
	 * @throws SQLException
	 */
    public void insertDrMemberBaseInfo(DrMemberBaseInfo drMemberBaseInfo) throws SQLException; 
    
    /**
     * 根据条件查询总数
     * @param map<String,Object>
     * @return Integer
     * @throws SQLException
     */
    public Integer queryMemberBaseInfoCountByMap(Map<String, Object> map) throws SQLException;
}