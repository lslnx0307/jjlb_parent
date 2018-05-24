package com.jjlb.model.dto.fuiou;

/**
 *
 * @author lslnx0307
 * @date 2018/5/24
 */

public class WithdrawalsDto {

    /**
     * fuiou平台账户
     */
    private String login_id;

    /**
     * 流水号
     */
    private String paymentNum;

    /**
     * 金额
     */
    private String amount;

    /**
     * 终端类型
     */
    private Integer channel;

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getPaymentNum() {
        return paymentNum;
    }

    public void setPaymentNum(String paymentNum) {
        this.paymentNum = paymentNum;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }
}
