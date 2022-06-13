package com.takaki.recruit.entity.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Takaki
 * @date 2022/6/11
 */
@Data
public class UserLogin {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
