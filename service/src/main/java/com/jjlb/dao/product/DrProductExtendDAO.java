package com.jjlb.dao.product;

import com.jjlb.model.entity.product.DrProductExtend;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface DrProductExtendDAO {
	
	/**
	 * 根据PID拿产品扩展信息
	 * @param  pid 产品ID
	 * @return List<DrProductExtend>
	 */
	public List<DrProductExtend> getDrProductExtendByPid(int pid);

    @Repository
    interface drProductInfoDao {
        int deleteByPrimaryKey(Integer id);

        int insert(drProductInfo record);

        int insertSelective(drProductInfo record);

        drProductInfo selectByPrimaryKey(Integer id);

        int updateByPrimaryKeySelective(drProductInfo record);

        int updateByPrimaryKey(drProductInfo record);
    }
}