package com.jjlb.dao.member;

import com.jjlb.model.entity.member.DrMemberMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@Repository
@Transactional
public interface DrMemberMsgDAO {
    /**
	 * @Description 添加
	 * @param drMemberMsg
	 * @return void
	 * @throws SQLException
	 */
    public void insertDrMemberMsg(DrMemberMsg drMemberMsg) throws SQLException;

    /**
     * 查询站内信
     * @param map
     * @return
     */
    public List<DrMemberMsg> getDrMemberListByParam(Map<String, Object> map);

    /**
     * 获取站内信总条数
     * @param map
     * @return
     */
    public int getDrMemberListCountByParam(Map<String, Object> map);
    
    /**
     * 修改消息为已读 
     * @param ids 当ids为空时，标记所有消息为已读
     * @param uid
     */
    public void updateMsgToReadByIds(@Param("ids") Integer[] ids, @Param("uid") Integer uid) throws SQLException; 
    
    /**
     * 删除用户选中消息
     * @param ids
     * @param uid
     * @throws SQLException
     */
    public void updateMsgToDelByIds(@Param("ids") Integer[] ids, @Param("uid") Integer uid) throws SQLException; 
    
    /**
     * 修改消息为已读
     * @param type 消息类型
     * @param uid	用户ID
     * @throws SQLException
     */
    public void updateMsgToRead(@Param("type") Integer type, @Param("uid") Integer uid) throws SQLException;

}