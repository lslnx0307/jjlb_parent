package com.jjlb.model.dto.fuiou;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author lslnx0307
 * @date 2018/5/23
 */
public class OnlineBankingRechargeResultDto {

    /**
     * signature
     */
    @ApiModelProperty(value = "signature")
    private String signature;

    /**
     * fuiouUrl
     */
    @ApiModelProperty(value = "fuiouUrl")
    private String fuiouUrl;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFuiouUrl() {
        return fuiouUrl;
    }

    public void setFuiouUrl(String fuiouUrl) {
        this.fuiouUrl = fuiouUrl;
    }
}
