package com.jjlb.model.dto.product;

import com.jjlb.common.PageBean;

/**
 *
 * @author lslnx0307
 * @date 2018/5/17
 */
public class ProductInfoDto extends PageBean{

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
