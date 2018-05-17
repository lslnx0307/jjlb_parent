package com.jjlb.model.dto.productInfo;

import com.jjlb.common.PageBean;

/**
 * Created by lslnx0307 on 2018/5/17.
 */
public class productInfoDto extends PageBean{

    private String fullName;

    private Integer type;

    private Integer status;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
