package com.zf.web.sysLog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zf.common.pagination.PageInfo;
import com.zf.common.pagination.Paging;
import com.zf.common.service.impl.BaseServiceImpl;
import com.zf.web.sysLog.entity.SysLog;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.zf.web.sysLog.mapper.SysLogMapper;
import com.zf.web.sysLog.param.SysLogPageParam;
import com.zf.web.sysLog.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统操作日志 服务实现类
 *
 * @author yss
 * @since 2021-10-22
 */
@Slf4j
@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysLog(SysLog sysLog) throws Exception {
        return super.save(sysLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysLog(SysLog sysLog) throws Exception {
        return super.updateById(sysLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysLog(int id) throws Exception {
        return super.updateById(new SysLog().setStatus("0").setId(id));
    }

    @Override
    public Paging<SysLog> getSysLogPageList(SysLogPageParam sysLogPageParam) throws Exception {
        Page<SysLog> page = new PageInfo<>(sysLogPageParam, OrderItem.desc(getLambdaColumn(SysLog::getCreateTime)));
        LambdaQueryWrapper<SysLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysLog::getStatus,"1");
        IPage<SysLog> iPage = sysLogMapper.selectPage(page, wrapper);
        return new Paging<SysLog>(iPage);
    }

}
