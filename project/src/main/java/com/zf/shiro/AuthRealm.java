package com.zf.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zf.exception.BaseException;
import com.zf.web.tUser.entity.TUser;
import com.zf.web.tUser.mapper.TUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * 自定义Realm类（Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。）
 *      1、用户身份认证（即登陆验证）
 *      2、权限认证授权
 *
 */
@Slf4j
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private TUserMapper tUserMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    /**
     * 整合jwt,必须重写此方法，不然报错
     * 该方法注明验证走AuthenticationToken
     * 即每次请求都需重弄验证身份（无状态）
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }
    /**
     * 该方法具体操作有：1、检查提交的进行认证的令牌信息
     * 2、根据令牌信息从数据源(通常为数据库)中获取用户信息
     * 3、对用户信息进行匹配验证。
     * 4、验证通过将返回一个封装了用户信息的 SimpleAuthenticationInfo 实例。
     * 5、验证失败则抛出 AuthenticationException 异常信息。
     *
     * @description 获取身份验证信息(即登陆验证)： ( 执行完Subject.login() 后进入此方法)
     * @param authenticationToken 用户身份信息 token
     * @return      返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //每次传来的token
        UsernamePasswordToken customizedToken = (UsernamePasswordToken) authenticationToken;
        String loginName = customizedToken.getUsername();
        if (loginName == null) {
            log.error("token中的用户不存在");
            throw new AuthenticationException("验证失败，请重新登录！");
        }
        String token = String.copyValueOf(customizedToken.getPassword());
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("admin:" + "token:" + token))) {
            log.error("redis中的token已过期或不存在");
            throw new AuthenticationException("验证失败，请重新登录！");
        }
        //查询用户信息
        TUser user = tUserMapper.selectOne(new QueryWrapper<TUser>().eq("status", "1").eq("username", loginName));
        //校验用户是否存在
        if (user == null) {
            throw new BaseException(500, "用户不存在");
        }
        log.info("进行身份验证时,用户提供的token有效");
        return new SimpleAuthenticationInfo(loginName, token, this.getName());
    }

    /**
     * @description 获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        System.out.println("————权限认证方法————");

        return null;
    }

}
