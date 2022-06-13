package com.takaki.recruit.constant;

/**
 * @author Takaki
 * @date 2022/6/10
 */
public interface JwtConstant {

    String TOKEN_PREFIX = "Bearer ";

    String AUTH_HEADER = "Authorization";

    String SECRET = "takaki@87449034";

    String ISSUER = "takaki";
    /**
     * 默认7天过期
     */
    Long EXPIRATION = 604800000L;
//    Long EXPIRATION = 10 * 1000L;
}
