package com.zf.web.sysLog.service;

import com.zf.common.pagination.Paging;
import com.zf.common.service.BaseService;
import com.zf.web.sysLog.entity.SysLog;
import com.zf.web.sysLog.param.SysLogPageParam;

/**
 * 系统操作日志 服务类
 *
 * @author yss
 * @since 2021-10-22
 */
public interface SysLogService extends BaseService<SysLog> {

    /**
     * 保存
     *
     * @param sysLog
     * @return
     * @throws Exception
     */
    boolean saveSysLog(SysLog sysLog) throws Exception;

    /**
     * 修改
     *
     * @param sysLog
     * @return
     * @throws Exception
     */
    boolean updateSysLog(SysLog sysLog) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysLog(int id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param sysLogPageParam
     * @return
     * @throws Exception
     */
    Paging<SysLog> getSysLogPageList(SysLogPageParam sysLogPageParam) throws Exception;

}
