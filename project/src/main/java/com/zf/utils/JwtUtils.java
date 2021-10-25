package com.zf.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * JWT工具类
 * @author yuzhian
 * @date 2021/10/25
 **/
public class JwtUtils {

    /** 过期时间 24 小时，与redis时间同步 */
    private static final long EXPIRE_TIME = 60 * 24 * 60 * 1000L;
    /** 密钥 */
    private static final String SECRET = "SHIRO+JWT";

    /**
     * 生成 token
     *
     * @param loginName 用户登录名
     * @return 加密的token
     */
    public static String createToken(String loginName,String password) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(password);
        // 附带loginName信息
        return JWT.create()
                .withClaim("loginName", loginName)
                //到期时间
                .withExpiresAt(date)
                //创建一个新的JWT，并使用给定的算法进行标记
                .sign(algorithm);
    }

    /**
     * 校验 token 是否正确
     *
     * @param token     密钥
     * @param loginName 用登录名
     * @return 是否正确
     */
    public static boolean verify(String token, String loginName) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("loginName", loginName)
                    .build();
            //验证 token
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @param token     密钥
     * @return token中包含的用户信息
     */
    public static String getLoginName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("loginName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}

