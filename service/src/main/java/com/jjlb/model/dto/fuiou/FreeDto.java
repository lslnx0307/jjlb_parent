package com.jjlb.model.dto.fuiou;

/**
 *  fuiou冻结dto
 * @author lslnx0307
 * @date 2018/5/24
 */
public class FreeDto {

    /**
     * 用户uid
     */
    private Integer uid;

    /**
     * 用户fuiou平台账户
     */
    private String cust_no;

    /**
     * 金额
     */
    private String amt;

    /**
     * 备注
     */
    private String rem;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getCust_no() {
        return cust_no;
    }

    public void setCust_no(String cust_no) {
        this.cust_no = cust_no;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getRem() {
        return rem;
    }

    public void setRem(String rem) {
        this.rem = rem;
    }
}
