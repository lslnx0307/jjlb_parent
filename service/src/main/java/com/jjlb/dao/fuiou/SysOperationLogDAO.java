package com.jjlb.dao.fuiou;


import com.jjlb.model.entity.fuiou.SysOperationLog;
import org.springframework.stereotype.Repository;

@Repository
public interface SysOperationLogDAO {

    public void saveSysLog(SysOperationLog sysOperationLog);

}
