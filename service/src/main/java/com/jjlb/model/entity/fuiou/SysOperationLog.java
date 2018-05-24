package com.jjlb.model.entity.fuiou;

import java.util.Date;

/**
 * 用户操作日志
 */
public class SysOperationLog {

    private Integer uid;
    private String operation;
    private Date addTime;

    public SysOperationLog() {
    }

    public SysOperationLog(Integer uid, String operation, Date addTime) {
        this.uid = uid;
        this.operation = operation;
        this.addTime = addTime;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getUid() {
        return uid;
    }

    public String getOperation() {
        return operation;
    }

    public Date getAddTime() {
        return addTime;
    }
}
