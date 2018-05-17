package com.jjlb.service.productInfo.impl;

import com.jjlb.common.BaseResult;
import com.jjlb.dao.productDao.drProductInfoDao;
import com.jjlb.model.dto.productInfo.productInfoDto;
import com.jjlb.service.productInfo.drProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lslnx0307 on 2018/5/17.
 */
@Service
@Transactional
public class drProductInfoServiceImpl implements drProductInfoService {

    @Autowired
    drProductInfoDao dao;

    @Override
    public BaseResult selectProductInfoList(productInfoDto dto) {
        return new BaseResult<String>(true, "100000", "帐户在其他设备登录");
    }
}
