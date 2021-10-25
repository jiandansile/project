package com.zf.web.sysLog.controller;

import com.alibaba.fastjson.JSON;
import com.zf.common.BaseController;
import com.zf.common.api.ApiResult;
import com.zf.annotation.OperLog;
import com.zf.common.pagination.Paging;
import com.zf.web.sysLog.entity.SysLog;
import com.zf.web.sysLog.param.SysLogPageParam;
import com.zf.web.sysLog.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * 系统操作日志 控制器
 *
 * @author yss
 * @since 2021-10-22
 */
@Slf4j
@RestController
@RequestMapping("/sysLog")
@Api(value = "系统操作日志API", tags = {"系统操作日志"})
public class SysLogController extends BaseController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 添加系统操作日志
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加系统操作日志", response = ApiResult.class)
    public ApiResult<Boolean> addSysLog(@Validated @RequestBody SysLog sysLog) throws Exception {
        boolean flag = sysLogService.saveSysLog(sysLog);
        return ApiResult.result(flag);
    }

    /**
     * 修改系统操作日志
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改系统操作日志", response = ApiResult.class)
    public ApiResult<Boolean> updateSysLog(@Validated @RequestBody SysLog sysLog) throws Exception {
        boolean flag = sysLogService.updateSysLog(sysLog);
        return ApiResult.result(flag);
    }

    /**
     * 删除系统操作日志
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除系统操作日志", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysLog(@PathVariable("id") int id) throws Exception {
        boolean flag = sysLogService.deleteSysLog(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取系统操作日志详情
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "系统操作日志详情", response = SysLog.class)
    public ApiResult<SysLog> getSysLog(@PathVariable("id") String id) throws Exception {
        SysLog sysLog = sysLogService.getById(id);
        System.out.println(this.getLoginName());
        System.out.println(this.getUserId());
        System.out.println(JSON.toJSON(this.getUserInfo()));
        return ApiResult.ok(sysLog);
    }

    /**
     * 系统操作日志分页列表
     */
    @PostMapping("/getPageList")
    @OperLog(operDesc = "日志")
    @ApiOperation(value = "系统操作日志分页列表", response = SysLog.class)
    public ApiResult<Paging<SysLog>> getSysLogPageList(@Validated @RequestBody SysLogPageParam sysLogPageParam) throws Exception {
        Paging<SysLog> paging = sysLogService.getSysLogPageList(sysLogPageParam);
        return ApiResult.ok(paging);
    }

}

