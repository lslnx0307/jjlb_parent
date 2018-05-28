package com.jjlb.dao.product;

import com.jjlb.model.entity.product.DrProductInfo;
import com.jjlb.model.entity.product.DrProductInvest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * @author lslnx0307
 */
@Repository
public interface DrProductInvestDAO {
	
	
	
	/**
	 * 根据投资记录id获取投资记录
	 * @param id
	 * @return
	 */
    public DrProductInvest selectByPrimaryKey(Map<String, Object> map);
	/**
	 * 得到投资记录
	 * @param Map
	 * @return List<DrProductInvest>
	 */
    public List<DrProductInvest> selectInvestLogByParam(Map<String, Object> map);

    public Integer selectInvestLogCountByParam(Map<String, Object> map);
    
    /**
     * 只插入有值得字段
     * @param invest
     */
    public void insertSelective(DrProductInvest invest);
    
    /**
     * 获取产品已投资人数
     * @param pid
     * @return
     */
    public Integer selectInvestNumsByPid(@Param("pid") Integer pid);
    
    /**
     * 通过状态查找用户对应本金总合
     * @param status
     * @param uid
     * @return
     */
    public BigDecimal selectUserSumPrincipalByStatus(Map<String, Object> map);
    
    /**
     * 通过状态查找用户对应利息总合
     * @param status
     * @param uid
     * @return
     */
    public BigDecimal selectUserSumInterestByStatus(Map<String, Object> map);
    
    /**
     * 根据map条件计算用户投资次数
     * @param map
     * @return
     */
    public Integer selectInvestCountByMap(Map<String, Object> map);
    
    /**
     * 用户最后一次投资信息
     * @param uid
     * @return
     */
    public Map<String,Object> selectUserLastInvestmentInfo(Integer uid);
    
    /**
     * 查询用户大于指定金额和期限的投资记录
     * @param param
     * @return
     */
    public List<DrProductInvest> selectInvestList(Map<String, Object> param);


	/**
	  * 查询投资记录前100条数据
	  * @return
	  */
	 public List<DrProductInfo>  selectInvest();
	 
	    /**
	     * 是否符合规则
	     * @return
	     */
    public int selectInvestCountExcludeNewHand(Integer uid);
    /**
     * 查询
     * @param param
     * @return
     */
    public List<DrProductInvest> selectInvestByMap(Map<String, Object> param);
    
    /**
     * 查询自己使用的体验金
     * @param uid
     * @param type
     * @param status
     * @return
     */
    public BigDecimal selectExperienceByStatus(@Param("uid") Integer uid, @Param("type") Integer type, @Param("status") Integer status);
    
    /**
	 * 判断用户投过新手标或者体验标
	 * @param param
	 * @return
	 */
	public List<DrProductInvest> checkProductType(Map<String, Object> param);
	/**
	 * 根据id查询非新手标和体验标的投资记录
	 * @param uid
	 * @return
	 */
	public int selectIsOldUserById(Integer uid);
	
	/**
	 * 查询体验标的投资人数
	 * @param pid
	 * @return
	 */
	public int selectExperInvestNumsByPid(Integer pid);
	
	public List<DrProductInvest> selectSimpleInvestLog(Integer pid);
	/**
	 * 根据条件查询是否可提现
	 * @param uid
	 * @return
	 */
	public Integer selectInvestCount(Integer uid);
	/**
	 * 查询投资体验标人的list
	 * @return
	 */
	public List<DrProductInvest> selectNoviceList();
	/**
	 * 投资体验标的人数
	 * @return
	 */
	public Integer selectNoviceCount();
	
	public Integer selectPrizeTypeByInvestId(Integer investId);
	
	/**
	 * 根据投资id查奖品详情
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> selectprizeInfoByInvestId(Map<String, Object> param);
	/**
	 * 根据uid查询用户投资投即送标的list
	 * @param uid
	 * @return
	 */
	public List<DrProductInvest> selectInvestSendDrProductInfoByUid(Integer uid);
	
	/**
	 * 根据投资id查询是否完善信息
	 * @param investId
	 * @return
	 */
	public Map<String,Object> selectIsPerfectByInvestId(Integer investId);
	/**
	 * 查询老投资
	 * @param map
	 * @return
	 */
	public List<DrProductInvest> selectOldInvest(Map<String, Object> map);
	/**
	 * 查询投即送没有地址的记录
	 * @param uid
	 * @return
	 */
	public List<DrProductInvest> selectInvestSendNotAddress(Integer uid);
	
	/**
	 * 查询除体验标之外的投资次数
	 * @param uid
	 * @return
	 */
	public Integer selectProductInvestCountByUid(Integer uid);
	
	/**
	 * 查询518累计投资
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectActivityMay18d(Map<String, Object> map);
	/**
	 * 查询518累计投资count
	 * @param map
	 * @return
	 */
	public int selectActivityMay18dCount(Map<String, Object> map);
	/**
	 * 查询518 个人累计投资
	 * @param map
	 * @return
	 */
	public int selectActivityMay18dInvestAmountByUid(Map<String, Object> map);
	/**
	 * 查询端午节活动投资记录
	 * @return
	 */
	public List<Map<String,Object>> selectInvestByDragonBoat(Map<String, Object> map);
	
	/**
	 * 根据uid获取投资投即送次数
	 * @param uid
	 * @return
	 */
	public Integer selectInvstSendCountByUid(Integer uid);
	
	/**
	 * 查询首投
	 * @param uid
	 * @return
	 */
	public Map<String,Object> selectInvestFirstByUid(Integer uid);
	/**
	 * 查询叱咤风云榜
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>>selectChiZhaList(Map<String, Object> map);
	/**
	 * 根据map查询除新手标、体验标以外标的数量
	 * @return
	 */
	public Map<String,Object> getInvestCount(Map<String, Object> map);
	
	/**
	 * 查询用户投资定期的记录数
	 * @param map
	 * @return
	 */
	public Integer getMemberInvestCount(Map<String, Object> map);
	/**
	 * 根据uid查询投资时间在3月2号与当前时间直接的投资的金额
	 * @param uid
	 * @return
	 */
	public List<Map<String, Object>> selectByUidForYuanXiao(Integer uid);
	/**
	 * 根据uid获取当前时间与元宵节addTime之间的投资金额
	 * @param uid
	 * @return
	 */
	public List<Map<String,Object>> selectCurrentInvest(Integer uid);

	/**
	 * 查询用户迎元宵期间投资次数(满足规则)
	 * @param uid
	 * @return
	 */
	public List<Map<String,Object>> getMemberYuanXiaoInvest(Integer uid);


	public List<Map<String, Object>> selectReturnCashByUid(Integer uid);


	/**
	 * 查询用户时间范围内的投资记录
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectInvestInTime(Map<String, Object> map);

	/**
	 * 根据产品ID查询未处理的投资记录
	 * @param pid
	 * @return
	 */
	public List<DrProductInvest> getFuiouDrProductInvestListByPid(@Param("pid")Integer pid);

	/**
	 * 通过ID批量修改投资记录状态
	 */
	public void updateStatusByIds(@Param("status")String status,@Param("ids")String[] ids);
}
