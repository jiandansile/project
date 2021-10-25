package com.zf.web.tUser.service;


import com.zf.common.pagination.Paging;
import com.zf.common.service.BaseService;
import com.zf.web.tUser.entity.TUser;
import com.zf.web.tUser.param.LoginParam;
import com.zf.web.tUser.param.TUserPageParam;

import java.util.Map;

/**
 * 用户表 服务类
 *
 * @author yss
 * @since 2021-10-22
 */
public interface TUserService extends BaseService<TUser> {

    /**
     * 保存
     *
     * @param tUser
     * @return
     * @throws Exception
     */
    boolean saveTUser(TUser tUser) throws Exception;

    /**
     * 修改
     *
     * @param tUser
     * @return
     * @throws Exception
     */
    boolean updateTUser(TUser tUser) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteTUser(int id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param tUserPageParam
     * @return
     * @throws Exception
     */
    Paging<TUser> getTUserPageList(TUserPageParam tUserPageParam) throws Exception;

    /**
     * 登录
     * @param loginParam
     * @return
     */
    Map<String, Object> login(LoginParam loginParam);

    /**
     * 登出
     * @param token
     * @return
     */
    boolean loginOut(String loginName,String token);
}
