package com.jjlb.service.redis.impl;


import com.jjlb.common.BaseResult;
import com.jjlb.common.PageInfo;
import com.jjlb.dao.fuiou.SysOperationLogDAO;
import com.jjlb.model.entity.fuiou.SysOperationLog;
import com.jjlb.service.redis.SysOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lslnx0307
 */
@Service
public class SysOperationLogServiceImpl implements SysOperationLogService {

    @Autowired
    private SysOperationLogDAO sysOperationLogDao;

    @Override
    public void saveSysLog(SysOperationLog sysOperationLog) {
        sysOperationLogDao.saveSysLog(sysOperationLog);
    }

    @Override
    public BaseResult getSysLogList(Map<String, Object> param, PageInfo pi) {
        return null;
    }

    @Override
    public List<SysOperationLog> exportSysLogList(Map<String, Object> param, PageInfo pi) {
        return null;
    }
}
