package com.jjlb.controller.productInfo;

import com.jjlb.model.dto.product.ProductInfoDto;
import com.jjlb.service.product.drProductInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author lslnx0307
 * @date 2018/5/17
 */
@RestController
@RequestMapping("/product")
public class DrProductInfoController {

    private Logger log = LoggerFactory.getLogger(DrProductInfoController.class);

    @Autowired
    drProductInfoService service;

    @RequestMapping( value = "/getProductInfoList",method = RequestMethod.POST)
    public String getProductInfoList(@RequestBody ProductInfoDto dto){
        System.out.println(dto.getType()+"");
        return "success";
    }
}
