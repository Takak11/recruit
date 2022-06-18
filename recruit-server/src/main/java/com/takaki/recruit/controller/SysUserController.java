package com.takaki.recruit.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.takaki.recruit.common.TransferPage;
import com.takaki.recruit.constant.ResponseStateConstant;
import com.takaki.recruit.constant.ResponseStateEnum;
import com.takaki.recruit.constant.RestResponse;
import com.takaki.recruit.entity.dto.user.*;
import com.takaki.recruit.entity.po.SysUserEntity;
import com.takaki.recruit.entity.vo.UserDetail;
import com.takaki.recruit.entity.vo.UserEdit;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public RestResponse userRegister(@RequestBody @Validated UserRegister info) throws BusinessBaseException {

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

        return RestResponse.success(userService.updateUserInfo(userTransfer));
    }

    @ApiOperation("上传用户头像")
    @PostMapping("/avatar")
    public RestResponse uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException, BusinessBaseException {

        String path = userService.updateUserAvatar(file);

        return null == path
                ? RestResponse.fail(ResponseStateConstant.ERROR_CODE, "上传头像失败")
                : RestResponse.success(MapUtil.of("src", path));
    }

    @ApiOperation("用户列表")
    @PostMapping("/list")
    public RestResponse userList(@RequestBody @Validated UserListPage page) {

        return RestResponse.success(userService.getUserList(page));
    }

    @ApiOperation("根据ID查询用户")
    @PostMapping("/detail")
    public RestResponse userDetail(@RequestBody @Validated UserId id) {
        System.out.println(id);
        UserDetail userDetail = new UserDetail();
        SysUserEntity entity = userService.getById(id.getId());
        if (entity != null) {
            BeanUtil.copyProperties(entity, userDetail);
            return RestResponse.success(userDetail);
        }

        return RestResponse.fail(ResponseStateConstant.ERROR_CODE, "服务器内部错误");
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public RestResponse userDelete(@RequestBody @Validated UserId id) {

        boolean result = userService.deleteUser(id);

        return RestResponse.success(MapUtil.of("result", result));
    }

    @ApiOperation("编辑用户")
    @PostMapping("/edit")
    public RestResponse userEdit(@RequestBody @Validated UserEdit edit) throws BusinessBaseException {
        boolean result = userService.editUser(edit);
        return RestResponse.success(MapUtil.of("result", result));
    }

    @ApiOperation("增加用户")
    @PostMapping("/add")
    public RestResponse userAdd(@RequestBody @Validated UserAdd user) {
        String username = userService.addUser(user);
        return RestResponse.success(MapUtil.of("username", username));
    }
}
