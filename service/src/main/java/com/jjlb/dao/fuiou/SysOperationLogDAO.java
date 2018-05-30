package com.jjlb.dao.fuiou;


import com.jjlb.model.entity.fuiou.SysOperationLog;
import org.springframework.stereotype.Repository;

/**
 * @author lslnx0307
 */
@Repository
public interface SysOperationLogDAO {

    /**
     * 保存sysLog
     * @param sysOperationLog
     */
     void saveSysLog(SysOperationLog sysOperationLog);

}
