package com.jjlb.model.dto.product;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 *
 * @author lslnx0307
 * @date 2018/5/23
 */
public class InvestDto {

    @ApiModelProperty(value = "用户uid")
    @NotEmpty(message = "notEmpty")
    private Integer uid;
    @ApiModelProperty(value = "产品id")
    @NotEmpty(message = "notEmpty")
    private Integer pid;
    @ApiModelProperty(value="投资金额")
    @NotEmpty(message = "notEmpty")
    private BigDecimal amount;
    @ApiModelProperty(value="优惠券id")
    @NotEmpty(message = "notEmpty")
    private Integer fid;
    @ApiModelProperty(value="终端类型")
    @NotEmpty(message = "notEmpty")
    private Integer channel;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }
}
