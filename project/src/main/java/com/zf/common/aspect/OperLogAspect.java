package com.zf.common.aspect;

import com.alibaba.fastjson.JSON;
import com.zf.annotation.OperLog;
import com.zf.utils.IpUtils;
import com.zf.web.sysLog.entity.SysLog;
import com.zf.web.sysLog.mapper.SysLogMapper;
import com.zf.web.tUser.entity.TUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 切面处理类，操作日志记录处理
 * @author yuzhian
 * @date 2021/10/25
 **/
@Aspect
@Component
public class OperLogAspect {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    SysLogMapper sysLogMapper;

    /** 统计请求的处理时间 */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
    * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
    */
    @Pointcut("@annotation(com.zf.annotation.OperLog)")
    public void operLogPoincut() {
    }


    @Before("operLogPoincut()")
    public void doBefore() {
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     */
    @AfterReturning(value = "operLogPoincut()")
    public void saveOperLog(JoinPoint joinPoint) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        // 执行消耗时间
        long time = System.currentTimeMillis() - startTime.get();
        startTime.remove();
        // 获取request
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 获取IP地址
        String ip = IpUtils.getIpAddr(request);
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取操作
        OperLog operLog = method.getAnnotation(OperLog.class);
        // 描述
        String operDesc = operLog.operDesc();
        // 用户名
        String username = (String) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        System.out.println(redisTemplate.opsForValue().get("admin:" + username + ":userInfo"));
        TUser tUser = JSON.parseObject(redisTemplate.opsForValue().get("admin:" + username + ":userInfo"), TUser.class);
        // 访问方法
        String className = joinPoint.getTarget().getClass().getName();
        String methodString = className + "." + signature.getName() + "()";
        // 保存操作记录
        SysLog sysLog = new SysLog();
        sysLog.setUserId(tUser.getId() + "").setLoginName(tUser.getUsername()).setModel(operDesc).setMethod(methodString).setTime((int) time).setIp(ip).setAddress(null);
        sysLogMapper.insert(sysLog);
    }
}
