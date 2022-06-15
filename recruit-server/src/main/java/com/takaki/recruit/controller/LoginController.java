package com.takaki.recruit.controller;

import cn.hutool.core.map.MapUtil;
import com.takaki.recruit.constant.RestResponse;
import com.takaki.recruit.entity.dto.user.UserLogin;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
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
@Slf4j
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

    @PostMapping("/logout")
    @ApiOperation("从服务端注销账号")
    public void logout() {
        log.info("调用注销接口");
        SecurityUtils.getSubject().logout();
    }
}
