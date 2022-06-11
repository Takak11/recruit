package com.takaki.recruit.service;

import cn.hutool.core.date.DateUtil;
import com.takaki.recruit.entity.dto.UserLogin;
import com.takaki.recruit.exception.BusinessBaseException;

import java.util.Date;

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
     * 获取token过期时间
     * @return token过期日期
     */
    String getExpiredDate();
}
