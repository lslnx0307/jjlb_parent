package com.jjlb.model.dto.fuiou;

/**
 * fuiou网银充值dto
 * @author lslnx0307
 * @date 2018/5/23
 */
public class OnlineBankingRechargeDto {

    /**
     * 流水号
     */
    private String mchnt_txn_ssn;

    /**
     * 金额
     */
    private String amt;

    /**
     * fuiou平台账户
     */
    private String login_id;

    /**
     * 银行代码
     */
    private String iss_ins_cd;

    /**
     * 支付类型
     */
    private String order_pay_type;

    public String getMchnt_txn_ssn() {
        return mchnt_txn_ssn;
    }

    public void setMchnt_txn_ssn(String mchnt_txn_ssn) {
        this.mchnt_txn_ssn = mchnt_txn_ssn;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
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
