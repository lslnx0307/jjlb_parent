package com.jjlb.dao.product;

import com.jjlb.model.entity.product.DrProductInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author lslnx0307
 */
@Repository
public interface DrProductInfoDAO {
	/**
	 * 查询 同类型的待上架产品
	 * 
	 * @return
	 */
	public List<DrProductInfo> selectProductInfo(Map<String, Object> param);

	/**
	 * 获取新手标信息
	 * 
	 * @return
	 */
	public DrProductInfo selectNewHandInfo(Map<String, Object> map);

	/**
	 * 获取产品列表
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> selectProductInfoListByParam(Map<String, Object> param);

	/**
	 * 查询90天活动标信息
	 * 
	 * @return
	 */
	public Map<String, Object> getActivityProductInfo();

	/**
	 * 产品列表总数
	 * 
	 * @param param
	 * @return
	 */
	public Integer selectProductInfoListCountByParam(Map<String, Object> param);

	/**
	 * 根据PID查找产品详情
	 * 
	 * @param pid
	 * @return
	 */
	public DrProductInfo selectProductDetailByPid(@Param(value = "id") Integer pid);

	/**
	 * 根据PID查找产品详情 不区分isshow
	 * 
	 * @param pid
	 * @return
	 */
	public DrProductInfo selectProductDetailById(@Param(value = "id") Integer pid);

	/**
	 * 通过产品ID查找产品详情
	 * 
	 * @param id
	 * @return
	 */
	public DrProductInfo selectProductByPrimaryKey(Integer id);

	/**
	 * 只修改已募集金额，剩余金额，产品状态 通过主键修改
	 * 
	 * @param info
	 *            DrProductInfo
	 */
	public void updateProductSelective(DrProductInfo info);

	public List<DrProductInfo> selectHotProductInfo();

	/**
	 * 查询推荐的产品，三个类型最早发布正在募集中的产品
	 * 
	 * @return
	 */
	public List<DrProductInfo> selectProductInfoRecommend();

	/**
	 * 查询活动产品
	 * 
	 * @param
	 * @return
	 */
	public DrProductInfo selectJSProductActive();

	/**
	 * 单一表查询
	 * 
	 * @param param
	 * @return
	 */
	public List<DrProductInfo> selectProductbyMap(Map<String, Object> param);

	/**
	 * 单一表查询
	 * 
	 * @param param
	 * @return
	 */
	public int selectProductCountbyMap(Map<String, Object> param);

	/**
	 * 查找体验标详情
	 * 
	 * @param
	 * @return
	 */
	public DrProductInfo selectExperienceDetail();

	/**
	 * 获取双蛋活动页产品
	 * 
	 * @param param
	 * @return
	 */
	public List<DrProductInfo> doubleEggList(Map<String, Object> param);

	public DrProductInfo selectPreferredInvest(Map<String, Object> param);

	/**
	 * 根据产品id获得 In_cust_no (借款人或企业的账户)
	 * 
	 * @param id
	 * @return
	 */
	public String getIn_cust_noByProductId(Integer id);

	/**
	 * 根据期限获得端午节产品
	 * 
	 * @param dealine
	 * @return
	 */
	public Map<String, Object> getProductInfoByDealine(Integer dealine);

	/**
	 * 查询秒杀标
	 * 
	 * @param
	 * @return
	 */
	public Map<String, Object> getProductMarking();

	/**
	 * 查询产品利率区间
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, Object> getProductRateInterval(Map<String, Object> map);

	/**
	 * 按期限分类取产品
	 * 
	 * @return
	 */
	public DrProductInfo selectPorductClassifyByDeadline(Map<String, Object> map);

	/**
	 * V标查询
	 * 
	 * @param param
	 * @return
	 */
	public DrProductInfo selectPreferredInvestV(Map<String, Object> param);

	/**
	 * 根据产品查询 借款人信息
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectBorrowingInfo(Map<String, Object> map);

	/**
	 * 查询借款信息总数
	 * 
	 * @param map
	 * @return
	 */
	public Integer selectBorrowingInfoCount(Map<String, Object> map);

	/**
	 * app首页获取定期产品
	 * 
	 * @param param
	 * @return
	 */
	public DrProductInfo selectDroproductByTimeASC(Map<String, Object> param);

	public DrProductInfo getDrProductLoanByMchntTxnSsn(Map<String, Object> map);

	public void updateDrProductLoanByMchntTxnSsn(Map<String, Object> map);

	/**
	 * 注册页查询产品列表
	 * 
	 * @param
	 * @return
	 */
	public List<String> selectNewRegisterForProduct();


	/**
	 * 根据id获取 DrProductInfo
	 * @param id
	 * @return
	 */
	public DrProductInfo selectProductById(Integer id);

	/**
	 * 查询募集完成的产品 以及募集中的新手产品
	 * @return
	 */
	public List<DrProductInfo> selectRaiseSuccesProductInfo();

	/**
	 * 查询要到期的产品
	 * @return
	 */
	public List<DrProductInfo> selectExpireProductInfo();

	/**
	 * 通过PID修改产品状态
	 * @return void
	 * @throws SQLException;
	 */
	public void updateDrProductInfoStatusById(@Param("status") Integer status, @Param("pid") Integer pid)
			throws SQLException;

	/**
	 * 更新产品回款状态
	 * @param  map
	 * @return void
	 * @throws SQLException;
	 */
	public void updateDrProductInfoInterestRepay(Map<String, Object> map);
}