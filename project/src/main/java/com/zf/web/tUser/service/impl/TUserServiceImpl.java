package com.zf.web.tUser.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zf.common.pagination.PageInfo;
import com.zf.common.pagination.Paging;
import com.zf.common.service.impl.BaseServiceImpl;
import com.zf.exception.BaseException;
import com.zf.utils.JwtUtils;
import com.zf.web.tUser.entity.TUser;
import com.zf.web.tUser.mapper.TUserMapper;
import com.zf.web.tUser.param.LoginParam;
import com.zf.web.tUser.param.TUserPageParam;
import com.zf.web.tUser.service.TUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户表 服务实现类
 *
 * @author yss
 * @since 2021-10-22
 */
@Slf4j
@Service
public class TUserServiceImpl extends BaseServiceImpl<TUserMapper, TUser> implements TUserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TUserMapper tUserMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveTUser(TUser tUser) throws Exception {
        return super.save(tUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateTUser(TUser tUser) throws Exception {
        return super.updateById(tUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteTUser(int id) throws Exception {
        return super.updateById(new TUser().setStatus("0").setId(id));
    }

    @Override
    public Paging<TUser> getTUserPageList(TUserPageParam tUserPageParam) throws Exception {
        Page<TUser> page = new PageInfo<>(tUserPageParam, OrderItem.desc(getLambdaColumn(TUser::getCreateTime)));
        LambdaQueryWrapper<TUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TUser::getStatus,"1");
        IPage<TUser> iPage = tUserMapper.selectPage(page, wrapper);
        return new Paging<TUser>(iPage);
    }

    @Override
    public Map<String, Object> login(LoginParam loginParam) {
        Map<String, Object> result = new HashMap<>();
        String userName = loginParam.getUsername();
        TUser tUser = tUserMapper.selectOne(new QueryWrapper<TUser>().eq("username", userName));
        if (tUser == null) {
            throw new BaseException(500, "该用户不存在");
        }
        String password = loginParam.getPassword();
        if (!password.equals(tUser.getPassword())) {
            throw new BaseException(500, "密码不正确");
        }
        String token = JwtUtils.createToken(userName,loginParam.getPassword());
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, token);
        redisTemplate.opsForValue().set("admin:token:" + token, token, 24, TimeUnit.HOURS);
        redisTemplate.opsForValue().set("admin:" + userName + ":userInfo", JSONObject.toJSONString(tUser), 24, TimeUnit.HOURS);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(usernamePasswordToken);
        SecurityUtils.getSubject().getSession().setTimeout(1 * 1000);
        result.putAll(JSON.parseObject(JSON.toJSONString(tUser), Map.class));
        result.put("token", token);
        return result;
    }

    @Override
    public boolean loginOut(String loginName,String token) {
        SecurityUtils.getSubject().logout();
        redisTemplate.delete("admin:" + "token:" + token);
        redisTemplate.delete("admin:" + loginName + ":userInfo");
        return true;
    }

}
