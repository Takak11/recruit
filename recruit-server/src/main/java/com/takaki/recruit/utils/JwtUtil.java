package com.takaki.recruit.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.takaki.recruit.constant.JwtConstant;
import com.takaki.recruit.constant.ResponseStateEnum;
import com.takaki.recruit.entity.dto.UserLogin;
import com.takaki.recruit.exception.BusinessBaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author Takaki
 * @date 2022/6/10
 */
@Slf4j
public class JwtUtil {
    /**
     * 将带Bearer前缀的token格式去掉，得到规范的token格式
     * @param rawToken
     * @return
     * @throws BusinessBaseException 当Header中的token没有Bearer前缀
     */
    public static String getFormattedToken(String rawToken) throws BusinessBaseException {
        boolean isValidToken = rawToken.contains(JwtConstant.TOKEN_PREFIX);
        if (isValidToken) {
            return StringUtils.delete(rawToken, JwtConstant.TOKEN_PREFIX);
        } else {
            throw new BusinessBaseException(ResponseStateEnum.ILLEGAL_ARGUMENT.getCode(), "Token格式有误");

        }
    }

    /**
     * 签发token
     * @param user
     * @return
     */
    public static String generateToken(UserLogin user) {
        Date expireDate = new Date(System.currentTimeMillis() + JwtConstant.EXPIRATION);

        return JWT.create()
                .withClaim("username", user.getUsername())
                .withIssuer(JwtConstant.ISSUER)
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(JwtConstant.SECRET));
    }

    /**
     * 验证token是否合法
     * @param token
     * @return
     */
    public static boolean verifyToken(String token) {

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JwtConstant.SECRET))
                    .withIssuer(JwtConstant.ISSUER)
                    .build();
            verifier.verify(token);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 从token中获取用户账号
     * @param token
     * @return
     */
    public static String getUsernameFromToken(String token) {

        return JWT.decode(token)
                .getClaim("username")
                .asString();
    }

    /**
     * 获取token过期时间
     * @param token
     * @return
     */
    public static Date getExpiredDate(String token) {
        return JWT.decode(token)
                .getExpiresAt();
    }

    public static void disableSysToken() {
    }
}
