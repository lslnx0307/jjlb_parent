package com.jjlb.model.dto.fuiou;

/**
 *
 * @author lslnx0307
 * @date 2018/5/23
 */
public class FastRechageDto {

    /**
     * 流水号
     */
    private String mchnt_txn_ssn;
    /**
     * 交易时间
     */
    private String txn_date;
    /**
     * 验证码
     */
    private String yzm;
    /**
     * 平台用户登陆账户
     */
    private String login_id;

    /**
     * 终端类型
     */
    private Integer channel;

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getMchnt_txn_ssn() {
        return mchnt_txn_ssn;
    }

    public void setMchnt_txn_ssn(String mchnt_txn_ssn) {
        this.mchnt_txn_ssn = mchnt_txn_ssn;
    }

    public String getTxn_date() {
        return txn_date;
    }

    public void setTxn_date(String txn_date) {
        this.txn_date = txn_date;
    }

    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

}
