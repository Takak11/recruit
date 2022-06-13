package com.takaki.recruit.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.takaki.recruit.common.JwtToken;
import com.takaki.recruit.entity.po.SysUserEntity;
import com.takaki.recruit.service.SysUserService;
import com.takaki.recruit.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Takaki
 * @date 2022/6/11
 */
@Slf4j
@Component("recruitRealm")
public class RecruitRealm extends AuthenticatingRealm {

    @Autowired
    private SysUserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getPrincipal();
        boolean isValid = JwtUtil.verifyToken(token);
        if (!isValid) {
            return null;
        }

        String username = JwtUtil.getUsernameFromToken(token);

        SysUserEntity userEntity = userService.getOne(
                new QueryWrapper<SysUserEntity>()
                        .eq("username", username)
        );
        if (userEntity == null) {
            return null;
        }
        log.info("{} ==> 查询用户", this.getClass());
        return new SimpleAuthenticationInfo(token, token, "recruitRealm");
    }
}
