package com.jjlb.service.productInfo;

import com.jjlb.common.BaseResult;
import com.jjlb.model.dto.productInfo.productInfoDto;

/**
 * Created by lslnx0307 on 2018/5/17.
 */

public interface drProductInfoService {

    /**
     * 查询列表
     * @param dto
     * @return
     */
    BaseResult selectProductInfoList(productInfoDto dto);
}
