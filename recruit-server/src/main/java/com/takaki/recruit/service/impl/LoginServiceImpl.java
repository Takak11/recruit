package com.takaki.recruit.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.takaki.recruit.common.JwtToken;
import com.takaki.recruit.constant.ResponseStateConstant;
import com.takaki.recruit.constant.ResponseStateEnum;
import com.takaki.recruit.entity.dto.mail.MailVerifier;
import com.takaki.recruit.entity.dto.user.ResetPassword;
import com.takaki.recruit.entity.dto.user.UserLogin;
import com.takaki.recruit.entity.po.MailEntity;
import com.takaki.recruit.entity.po.SysUserEntity;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.mapper.MailMapper;
import com.takaki.recruit.mapper.SysUserMapper;
import com.takaki.recruit.service.LoginService;
import com.takaki.recruit.service.SysUserService;
import com.takaki.recruit.utils.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Takaki
 * @date 2022/6/11
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private MailMapper mailMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public String userLogin(UserLogin user) throws BusinessBaseException {
        SysUserEntity userEntity = userService
                .getOne(
                        new QueryWrapper<SysUserEntity>()
                                .eq("username", user.getUsername())
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
    public String userLoginWithMail(MailVerifier verifier) throws BusinessBaseException {

        SysUserEntity entity = null;
        try {
            entity = userService.getOne(
                    new QueryWrapper<SysUserEntity>()
                            .eq("mail", verifier.getMail())
                            .eq("sms", verifier.getSms())
            );

            if (ObjectUtil.isNull(entity)) {
                throw new BusinessBaseException(ResponseStateConstant.ERROR_CODE, "该邮箱未注册或内部错误");
            }

        } catch (Exception e) {
            throw new BusinessBaseException(ResponseStateConstant.ERROR_CODE, "该邮箱未注册或内部错误");
        }
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(entity.getUsername());

        String token = JwtUtil.generateToken(userLogin);

        SecurityUtils.getSubject().logout();
        SecurityUtils.getSubject().login(new JwtToken(token));
        return token;
    }

    @Override
    public String resetPassword(ResetPassword bean) throws BusinessBaseException {


        List<SysUserEntity> userEntities = userMapper.selectList(
                new QueryWrapper<SysUserEntity>()
                        .eq("username", bean.getUsername())
                        .eq("mail", bean.getMail())
        );
        if (userEntities.size() != 1) {
            throw new BusinessBaseException(ResponseStateConstant.ERROR_CODE, "该用户未注册");
        }
        SysUserEntity entity = userEntities.get(0);

        List<MailEntity> mailEntities = mailMapper.selectList(
                new QueryWrapper<MailEntity>()
                        .eq("email", bean.getMail())
                        .eq("code", bean.getSms())
        );
        if (mailEntities.size() != 1) {
            throw new BusinessBaseException(ResponseStateConstant.ERROR_CODE, "验证码错误");
        }

        String password = BCrypt.hashpw(bean.getPassword());
        entity.setPassword(password);
        userMapper.updateById(entity);

        return password;
    }
}
