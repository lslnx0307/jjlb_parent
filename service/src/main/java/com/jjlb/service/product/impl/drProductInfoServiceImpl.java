package com.jjlb.service.product.impl;

import com.jjlb.common.BaseResult;
import com.jjlb.dao.product.DrProductExtendDAO;
import com.jjlb.model.dto.product.ProductInfoDto;
import com.jjlb.service.product.drProductInfoService;
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
    DrProductExtendDAO.drProductInfoDao dao;

    @Override
    public BaseResult selectProductInfoList(ProductInfoDto dto) {
        return new BaseResult<String>(true, "100000", "帐户在其他设备登录");
    }
}
