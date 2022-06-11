package com.takaki.recruit.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.takaki.recruit.constant.ResponseStateEnum;
import com.takaki.recruit.entity.dto.UserLogin;
import com.takaki.recruit.entity.po.SysUserEntity;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.service.LoginService;
import com.takaki.recruit.service.SysUserService;
import com.takaki.recruit.utils.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Takaki
 * @date 2022/6/11
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService userService;

    @Override
    public String userLogin(UserLogin user) throws BusinessBaseException {
        SysUserEntity userEntity = userService
                .getOne(
                        new QueryWrapper<SysUserEntity>()
                                .eq("mail", user.getUsername())
                );
        boolean isValid;
        try {
            isValid = BCrypt.checkpw(user.getPassword(), userEntity.getPassword());
        } catch (Exception e) {
            throw new BusinessBaseException(ResponseStateEnum.ILLEGAL_ARGUMENT.getCode(), "用户名或密码错误");
        }

        if (isValid) {
            return JwtUtil.generateToken(user);
        } else {
            throw new BusinessBaseException(ResponseStateEnum.ILLEGAL_ARGUMENT.getCode(), "用户名或密码错误");
        }
    }

    @Override
    public String getExpiredDate() {

        String token = SecurityUtils
                .getSubject()
                .getPrincipal()
                .toString();

        Date expiredDate = JwtUtil.getExpiredDate(token);
        return DateUtil.format(expiredDate, "yyyy-MM-dd HH:mm:ss");


    }

    public static void main(String[] args) {
    }
}
