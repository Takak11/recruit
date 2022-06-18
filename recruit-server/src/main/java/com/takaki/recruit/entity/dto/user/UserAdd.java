package com.takaki.recruit.entity.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Takaki
 * @date 2022/6/18
 */
@Data
public class UserAdd {
    @NotBlank(message = "邮件号码为空")
    private String mail;
    @NotBlank(message = "手机号码为空")
    private String mobile;
    @NotBlank(message = "昵称为空")
    private String name;
    @NotBlank(message = "密码为空")
    private String password;
    @NotNull(message = "年龄为空")
    private Integer age;
    @NotNull(message = "性别为空")
    private Integer sex;
}
