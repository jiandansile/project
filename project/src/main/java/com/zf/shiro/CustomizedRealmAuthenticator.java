package com.zf.shiro;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

/**
 * 定制token登录鉴权
 * @author yuzhian
 * @date 2021/10/25
 */
@Slf4j
public class CustomizedRealmAuthenticator extends ModularRealmAuthenticator {

    /**
     * 当subject.login()方法执行,下面的代码即将执行
     */
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 判断getRealms()是否返回为空
        this.assertRealmsConfigured();
        // 所有Realm
        Collection<Realm> realmList = this.getRealms();
        // 这个类型转换的警告不需要再关注 如果token错误 后面将会抛出异常信息
        UsernamePasswordToken customizedToken = (UsernamePasswordToken) authenticationToken;
        return this.doMultiRealmAuthentication(realmList, customizedToken);
    }
}
