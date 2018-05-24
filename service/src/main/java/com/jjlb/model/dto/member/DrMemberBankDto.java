package com.jjlb.model.dto.member;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 *  用户开户绑卡的bean
 * @author lslnx0307
 * @date 2018/5/22
 */
public class DrMemberBankDto {

    @ApiModelProperty(value="用户uid")
    @NotEmpty(message="notEmpty")
    private Integer uid;

    @ApiModelProperty(value="终端类型: 0:pc 1:ios 2:android 3:h5 5:微信")
    @NotEmpty(message="notEmpty")
    private Integer channel;

    @ApiModelProperty(value="用户姓名")
    @NotEmpty(message="notEmpty")
    private String cust_nm;

    @ApiModelProperty(value="身份证号码")
    @NotEmpty(message="notEmpty")
    private String certif_id;

    @ApiModelProperty(value="交易密码")
    @NotEmpty(message="notEmpty")
    private String password;

    @ApiModelProperty(value="重复交易密码")
    @NotEmpty(message="notEmpty")
    private String rpassword;

    @ApiModelProperty(value="开户行地区代码")
    @NotEmpty(message="notEmpty")
    private String city_id;

    @ApiModelProperty(value="开户行行别")
    @NotEmpty(message="notEmpty")
    private String parent_bank_id;

    @ApiModelProperty(value="帐号")
    @NotEmpty(message="notEmpty")
    private String capAcntNo;

    @ApiModelProperty(value="预留手机号码")
    @NotEmpty(message="notEmpty")
    @Pattern(regexp="[1][3456789]\\d{9}",message="app.phone.format.error")
    private String mobile_no;

    @ApiModelProperty(value = "",hidden = true)
    private String mchnt_txn_ssn;

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

    public String getCust_nm() {
        return cust_nm;
    }

    public void setCust_nm(String cust_nm) {
        this.cust_nm = cust_nm;
    }

    public String getCertif_id() {
        return certif_id;
    }

    public void setCertif_id(String certif_id) {
        this.certif_id = certif_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRpassword() {
        return rpassword;
    }

    public void setRpassword(String rpassword) {
        this.rpassword = rpassword;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getParent_bank_id() {
        return parent_bank_id;
    }

    public void setParent_bank_id(String parent_bank_id) {
        this.parent_bank_id = parent_bank_id;
    }

    public String getCapAcntNo() {
        return capAcntNo;
    }

    public void setCapAcntNo(String capAcntNo) {
        this.capAcntNo = capAcntNo;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getMchnt_txn_ssn() {
        return mchnt_txn_ssn;
    }

    public void setMchnt_txn_ssn(String mchnt_txn_ssn) {
        this.mchnt_txn_ssn = mchnt_txn_ssn;
    }
}
