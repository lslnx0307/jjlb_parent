package com.jjlb.dao.productDao;

import com.jjlb.model.entity.productInfo.drProductInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface drProductInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(drProductInfo record);

    int insertSelective(drProductInfo record);

    drProductInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(drProductInfo record);

    int updateByPrimaryKey(drProductInfo record);
}