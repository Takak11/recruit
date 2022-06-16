package com.takaki.recruit.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.takaki.recruit.constant.JwtConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Takaki
 * @date 2022/6/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken implements AuthenticationToken {
    private String token;


    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
