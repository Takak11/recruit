package com.takaki.recruit.controller;

import cn.hutool.core.map.MapUtil;
import com.takaki.recruit.constant.RestResponse;
import com.takaki.recruit.entity.dto.UserLogin;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Takaki
 * @date 2022/6/11
 */
@Api(tags = "登录管理接口")
@RestController
@RequestMapping("/api/recruit")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ApiOperation("用户名密码方式登录")
    public RestResponse login(@RequestBody @Validated UserLogin user) throws BusinessBaseException {
        String token = loginService.userLogin(user);

        return RestResponse.success(MapUtil.of("token", token));
    }

    @PostMapping("/token/expired")
    @ApiOperation("获取当前用户token过期时间")
    public RestResponse getExpiredDate() {

        return RestResponse.success(MapUtil.of("expiredDate", loginService.getExpiredDate()));
    }
}
