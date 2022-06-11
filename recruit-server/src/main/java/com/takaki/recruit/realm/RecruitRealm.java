package com.takaki.recruit.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.takaki.recruit.common.JwtToken;
import com.takaki.recruit.constant.ResponseStateEnum;
import com.takaki.recruit.entity.po.SysUserEntity;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.service.SysUserService;
import com.takaki.recruit.utils.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * @author Takaki
 * @date 2022/6/11
 */
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

        userService.getOne(new QueryWrapper<SysUserEntity>().eq("mail", username));
        SysUserEntity userEntity = userService.getOne(
                new QueryWrapper<SysUserEntity>()
                        .eq("mail", username)
        );
        if (userEntity == null) {
            return null;
        }

        return new SimpleAuthenticationInfo(token, token, "recruitRealm");
    }
}
