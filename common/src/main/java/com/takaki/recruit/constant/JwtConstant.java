package com.takaki.recruit.constant;

/**
 * @author Takaki
 * @date 2022/6/10
 */
public interface JwtConstant {

    String AUTH_HEADER = "Authorization";

    String SECRET = "defaultSecret";

    /**
     * 默认7天过期
     */
    Long EXPIRATION = 604800L;
}
