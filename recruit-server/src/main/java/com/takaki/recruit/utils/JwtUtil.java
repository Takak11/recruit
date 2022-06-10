package com.takaki.recruit.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.takaki.recruit.constant.JwtConstant;
import com.takaki.recruit.entity.po.SysUserEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author Takaki
 * @date 2022/6/10
 */
@Slf4j
public class JwtUtil {
    public static String generateToken(SysUserEntity user) {
        Date expireDate = new Date(System.currentTimeMillis() + JwtConstant.EXPIRATION);

        return JWT.create()
                .withClaim("username", user.getMail())
                .withIssuer(JwtConstant.ISSUER)
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(JwtConstant.SECRET));
    }

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

    public static String getUsernameFromToken(String token) {

        return JWT.decode(token)
                .getClaim("username")
                .asString();
    }
}
