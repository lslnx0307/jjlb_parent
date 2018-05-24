package com.jjlb.model.dto.member;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户快捷充值dto
 * @author lslnx0307
 * @date 2018/5/23
 */

public class DrMemberFastRechargeDto {

    @ApiModelProperty(value="用户uid")
    @NotEmpty(message="notEmpty")
    private Integer uid;

    @ApiModelProperty(value="终端类型 0:pc 1:ios 2:android 3:h5 5:微信")
    @NotEmpty(message="notEmpty")
    private Integer Channel;

    @ApiModelProperty(value = "验证码")
    @NotEmpty(message="notEmpty")
    private String yzm;

    @ApiModelProperty(value = "金额")
    @NotEmpty(message="notEmpty")
    private String amt;

    @ApiModelProperty(value = "订单号")
    @NotEmpty(message="notEmpty")
    private String code;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getChannel() {
        return Channel;
    }

    public void setChannel(Integer channel) {
        Channel = channel;
    }

    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
