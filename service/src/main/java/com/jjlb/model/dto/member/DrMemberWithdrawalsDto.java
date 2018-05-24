package com.jjlb.model.dto.member;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 *
 * @author lslnx0307
 * @date 2018/5/24
 */
public class DrMemberWithdrawalsDto {

    /**
     * 用户uid
     */
    @ApiModelProperty(value = "用户uid")
    @NotEmpty(message = "notEmpty")
    private Integer uid;

    /**
     * 终端类型
     */
    @ApiModelProperty(value = "终端类型 ")
    @NotEmpty(message = "notEmpty")
    private Integer channel;

    /**
     * 金额
     */
    @ApiModelProperty(value = "提现金额")
    @NotEmpty(message = "notEmpty")
    private BigDecimal amount;

    /**
     * 是否需要手续费
     */
    @ApiModelProperty(value = "是否需要手续费 0否 1是")
    @NotEmpty(message = "notEmpty")
    private Integer isChargeFlag;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getIsChargeFlag() {
        return isChargeFlag;
    }

    public void setIsChargeFlag(Integer isChargeFlag) {
        this.isChargeFlag = isChargeFlag;
    }
}
