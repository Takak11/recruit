package com.takaki.recruit.entity.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Takaki
 * @date 2022/6/13
 */
@Data
public class UserPassword {
    @NotBlank(message = "旧密码为空")
    private String currentPassword;
    @NotBlank(message = "新密码为空")
    private String newPassword;
}
