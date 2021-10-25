package com.zf.conf;

import com.zf.filter.JwtFilter;
import com.zf.shiro.AuthRealm;
import com.zf.shiro.CustomizedRealmAuthenticator;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;
import java.util.*;

/**
 * @description Shiro 配置类
 * @author yuzhian
 * @date 2021/10/25
 */
@Configuration
public class ShiroConfig {

    /**
     * @description Shiro Filter 拦截器相关配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager")SecurityManager securityManager) {
        System.out.println("-------------ShiroConfig.shiroFilter() 开始------------");

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置shiro 安全管理器 (必须设置)
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置默认登录页面(如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射)
        shiroFilterFactoryBean.setLoginUrl("/login");

        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized/无权限");
        /**自定义过滤器 (过滤链定义，从上向下顺序执行，一般将 "/**" 放在最为下边,这是一个坑呢，一不小心代码就不好使了)*/
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        /* 设置 LoginController 里不被拦截的url   start*/
        filterChainDefinitionMap.put("/vendor/**", "anon");
        filterChainDefinitionMap.put("/tUser/login", "anon");
        filterChainDefinitionMap.put("/tUser/loginOut", "anon");
        /* 设置 controller 里不被拦截的url   end*/
        filterChainDefinitionMap.put("/**", "jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        System.out.println("-------------Shiro拦截器工厂类注入成功-------------");
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
        //设置我们自定义的JWT过滤器
        filterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * @description 注入 shiro的核心安全事务管理器 securityManager
     *      1、 自定义的 realm
     *      2、 自定义session管理
     */
    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("myShiroRealm") AuthRealm authRealm,
                                           @Qualifier("customizedRealmAuthenticator") CustomizedRealmAuthenticator authenticator) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义 realm
        securityManager.setAuthenticator(authenticator);
        List<Realm> realmList = new ArrayList<Realm>();
        // 添加多个realm
        realmList.add(authRealm);
        // realmList.add(customerRealm);
        securityManager.setRealms(realmList);
        //关闭shiro自带的session，详情见文档
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * @description 2、注入自定义Realm类：AuthRealm
     */
    @Bean(name="myShiroRealm")
    public AuthRealm myShiroRealm() {
        return new AuthRealm();
    }

    @Bean
    public CustomizedRealmAuthenticator customizedRealmAuthenticator() {
        //自己重写的ModularRealmAuthenticator
        CustomizedRealmAuthenticator authenticator = new CustomizedRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return authenticator;
    }

    /**
     * 添加注解支持
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
