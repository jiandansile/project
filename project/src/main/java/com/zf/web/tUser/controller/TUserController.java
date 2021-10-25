package com.zf.web.tUser.controller;

import com.zf.common.BaseController;
import com.zf.common.api.ApiResult;
import com.zf.annotation.OperLog;
import com.zf.common.pagination.Paging;
import com.zf.web.tUser.entity.TUser;
import com.zf.web.tUser.param.LoginParam;
import com.zf.web.tUser.param.TUserPageParam;
import com.zf.web.tUser.service.TUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户表 控制器
 *
 * @author yss
 * @since 2021-10-22
 */
@Slf4j
@RestController
@RequestMapping("/tUser")
@Api(value = "用户表API", tags = {"用户表"})
public class TUserController extends BaseController {

    @Autowired
    private TUserService tUserService;

    /**
     * 添加用户表
     */
    @PostMapping("/add")
    @OperLog(operDesc = "添加用户表")
    @ApiOperation(value = "添加用户表", response = ApiResult.class)
    public ApiResult<Boolean> addTUser(@Validated @RequestBody TUser tUser) throws Exception {
        boolean flag = tUserService.saveTUser(tUser);
        return ApiResult.result(flag);
    }

    /**
     * 修改用户表
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改用户表", response = ApiResult.class)
    public ApiResult<Boolean> updateTUser(@Validated @RequestBody TUser tUser) throws Exception {
        boolean flag = tUserService.updateTUser(tUser);
        return ApiResult.result(flag);
    }

    /**
     * 删除用户表
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除用户表", response = ApiResult.class)
    public ApiResult<Boolean> deleteTUser(@PathVariable("id") int id) throws Exception {
        boolean flag = tUserService.deleteTUser(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取用户表详情
     */
    @GetMapping("/info/{id}")
    @OperLog(operDesc = "用户表详情")
    @ApiOperation(value = "用户表详情", response = TUser.class)
    public ApiResult<TUser> getTUser(@PathVariable("id") String id) throws Exception {
        TUser tUser = tUserService.getById(id);
        return ApiResult.ok(tUser);
    }

    /**
     * 用户表分页列表
     */
    @PostMapping("/getPageList")
    @OperLog(operDesc = "用户表分页列表")
    @ApiOperation(value = "用户表分页列表", response = TUser.class)
    public ApiResult<Paging<TUser>> getTUserPageList(@Validated @RequestBody TUserPageParam tUserPageParam) throws Exception {
        Paging<TUser> paging = tUserService.getTUserPageList(tUserPageParam);
        return ApiResult.ok(paging);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    @OperLog(operDesc = "登录")
    @ApiOperation(value = "登录", response = TUser.class)
    public ApiResult login(@Validated @RequestBody LoginParam loginParam) throws Exception {
        Map<String, Object> token = tUserService.login(loginParam);
        return ApiResult.ok(token);
    }

    /**
     * 登出
     */
    @PostMapping("/loginOut")
    @OperLog(operDesc = "登出")
    @ApiOperation(value = "登出", response = TUser.class)
    public ApiResult loginOut(@RequestHeader("token") String token) throws Exception {
        boolean flag = tUserService.loginOut(this.getLoginName(),token);
        return ApiResult.result(flag);
    }

}

