package com.takaki.recruit.service;

import com.takaki.recruit.entity.dto.mail.MailVerifier;
import com.takaki.recruit.entity.dto.user.ResetPassword;
import com.takaki.recruit.entity.dto.user.UserLogin;
import com.takaki.recruit.exception.BusinessBaseException;

/**
 * @author Takaki
 * @date 2022/6/11
 */
public interface LoginService {
    /**
     * 接收用户名和密码、返回一个JWTToken
     * @param user 用户名密码DTO
     * @return token
     * @throws BusinessBaseException 校验失败时
     */
    String userLogin(UserLogin user) throws BusinessBaseException;

    /**
     * 根据Mail查询数据库，生成Token，登录并返回
     * @param verifier
     * @return
     * @throws BusinessBaseException
     */
    String userLoginWithMail(MailVerifier verifier) throws BusinessBaseException;

    /**
     * 重置密码
     * @param bean
     * @return
     * @throws BusinessBaseException
     */
    String resetPassword(ResetPassword bean) throws BusinessBaseException;
}
