package com.zf.common;

import com.alibaba.fastjson.JSONObject;
import com.zf.web.tUser.entity.TUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 基础
 * @author yuzhian
 * @date 2021/10/25
 **/
public class BaseController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    /**
     * 获取当前登录账号
     * @return  登录账号
     */
    protected String getLoginName() {
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipals() != null) {
            return  (String) subject.getPrincipals().getPrimaryPrincipal();
        } else {
            return null;
        }
    }

    /**
     * 获取当前登录用户信息
     * @return  用户信息
     */
    public TUser getUserInfo() {
        String loginName = this.getLoginName();
        String userInfo = redisTemplate.opsForValue().get("admin:" + loginName + ":userInfo");
        return JSONObject.parseObject(userInfo, TUser.class);
    }

    /**
     * 获取当前登录用户id
     * @return  用户id
     */
    public int getUserId() {
        return this.getUserInfo().getId();
    }
}
