package com.jjlb.service.redis;


import com.jjlb.common.BaseResult;
import com.jjlb.common.PageInfo;
import com.jjlb.model.entity.fuiou.SysOperationLog;

import java.util.List;
import java.util.Map;

public interface SysOperationLogService {

    public void saveSysLog(SysOperationLog sysOperationLog);

    public BaseResult getSysLogList(Map<String, Object> param, PageInfo pi);

    public List<SysOperationLog> exportSysLogList(Map<String, Object> param, PageInfo pi);
}
