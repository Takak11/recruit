package com.takaki.recruit.controller;

import cn.hutool.core.map.MapUtil;
import com.takaki.recruit.constant.ResponseStateConstant;
import com.takaki.recruit.constant.ResponseStateEnum;
import com.takaki.recruit.constant.RestResponse;
import com.takaki.recruit.entity.dto.user.UserPassword;
import com.takaki.recruit.entity.dto.user.UserRegister;
import com.takaki.recruit.entity.dto.user.UserTransfer;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Takaki
 * @since 2022-06-09
 */
@Api(tags = {"用户管理相关接口"})
@RestController
@RequestMapping("/api/recruit/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @PostMapping("/token/expired")
    @ApiOperation("获取当前用户token过期时间")
    public RestResponse getExpiredDate() {

        return RestResponse.success(MapUtil.of("expiredDate", userService.getExpiredDate()));
    }

    @ApiOperation("修改密码接口")
    @PostMapping("/password")
    public RestResponse changePassword(@RequestBody @Validated UserPassword userPassword) throws BusinessBaseException {

        return userService.renewPassword(userPassword)
                ? RestResponse.success(true)
                : RestResponse.fail(ResponseStateEnum.INTERNAL_SERVER_ERROR, false);
    }

    @ApiOperation("用户注册接口")
    @PostMapping("/register")
    public RestResponse userRegister(@RequestBody @Validated UserRegister info) {

        String username = userService.register(info);
        return null == username
                ? RestResponse.fail(ResponseStateConstant.ERROR_CODE, "注册失败")
                : RestResponse.success(MapUtil.of("username", username));
    }

    @ApiOperation("获取用户信息")
    @PostMapping("/info")
    public RestResponse getUserInfo() throws BusinessBaseException {

        return RestResponse.success(userService.getUserInfo());
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/update")
    public RestResponse updateUserInfo(@RequestBody @Validated UserTransfer userTransfer) throws BusinessBaseException {

        userService.updateUserInfo(userTransfer);
        return RestResponse.success();
    }

    @ApiOperation("上传用户头像")
    @PostMapping("/avatar")
    public RestResponse uploadAvatar(@RequestParam("avatar") MultipartFile file) throws IOException, BusinessBaseException {

        String path = userService.updateUserAvatar(file);

        return null == path
                ? RestResponse.fail(ResponseStateConstant.ERROR_CODE, "上传头像失败")
                : RestResponse.success(path);
    }
}
