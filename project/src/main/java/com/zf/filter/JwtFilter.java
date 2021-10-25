package com.zf.filter;

import com.zf.exception.BaseException;
import com.zf.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT自定义过滤器
 * @author yuzhian
 * @date 2021/10/25
 **/
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {
    /**
     * 执行登录认证
     * @param request ServletRequest
     * @param response ServletResponse
     * @param mappedValue mappedValue
     * @return 是否成功
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            return this.executeLogin(request, response);
        } catch (Exception e) {
            throw new BaseException(401,"token过期或异常登录");
        }
    }

    /**
     * 执行登陆操作
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        if(requestURI.contains("swagger-ui")
                || requestURI.contains("swagger-resources")
                || requestURI.contains("v2/api-docs")
                || requestURI.contains("springfox-swagger-ui")
                || requestURI.contains("configuration/security")
                || requestURI.contains("configuration/ui")
                || ("/").equals(requestURI)
                || ("/csrf").equals(requestURI)
                || ("/error").equals(requestURI)
        ){
            return true;
        }
        String token = httpServletRequest.getHeader("token");
        log.info("获取到的token是:{}",token);
        if (token == null) {
            throw new BaseException(401,"token过期或异常登录");
        }
        String loginName = JwtUtils.getLoginName(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        try {
            this.getSubject(request, response).login(new UsernamePasswordToken(loginName, token));
        } catch (Exception e) {
            throw new BaseException(401,"token过期或异常登录");
        }
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
