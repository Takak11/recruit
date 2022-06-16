package com.takaki.recruit.entity.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Takaki
 * @date 2022/6/13
 */
@Data
public class UserRegister {
    @NotBlank(message = "邮箱号不能为空")
    private String mail;
    @NotBlank(message = "手机号不能为空")
    private String mobile;
    @NotBlank(message = "昵称不能为空")
    private String name;
    @NotBlank(message = "密码不能为空")
    private String password;
}
