package com.jjlb.model.dto.member;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 网银充值dto
 * @author lslnx0307
 * @date 2018/5/23
 */

public class DrMemberOnlineBankingRechargeDto {

    /**
     * 用户uid
     */
    @ApiModelProperty(value = "用户uid")
    @NotEmpty(message="notEmpty")
    private Integer uid;
    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    @NotEmpty(message="notEmpty")
    private String amt;
    /**
     * 银行代码
     */
    @ApiModelProperty(value = "银行代码")
    @NotEmpty(message="notEmpty")
    private String iss_ins_cd;
    /**
     * 支付类型
     */
    @ApiModelProperty(value = "支付类型")
    @NotEmpty(message="notEmpty")
    private String order_pay_type;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getIss_ins_cd() {
        return iss_ins_cd;
    }

    public void setIss_ins_cd(String iss_ins_cd) {
        this.iss_ins_cd = iss_ins_cd;
    }

    public String getOrder_pay_type() {
        return order_pay_type;
    }

    public void setOrder_pay_type(String order_pay_type) {
        this.order_pay_type = order_pay_type;
    }
}
