package com.jjlb.controller.productInfo;

import com.jjlb.model.dto.productInfo.productInfoDto;
import com.jjlb.service.productInfo.drProductInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by lslnx0307 on 2018/5/17.
 */
@RestController
@RequestMapping("/product")
public class drProductInfoController {

    private Logger log = LoggerFactory.getLogger(drProductInfoController.class);

    @Autowired
    drProductInfoService service;

    @RequestMapping( value = "/getProductInfoList",method = RequestMethod.POST)
    public String getProductInfoList(@RequestBody productInfoDto dto){
        System.out.println(dto.getType()+"");
        return "success";
    }
}
