package com.takaki.recruit.entity.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Takaki
 * @date 2022/6/13
 */
@Data
@EqualsAndHashCode
public class UserTransfer {
    @NotBlank(message = "姓名为空")
    private String name;
    @NotBlank(message = "邮箱为空")
    private String mail;
    @NotBlank(message = "手机号为空")
    private String mobile;
    @NotNull(message = "年龄为空")
    private Integer age;
    @NotNull(message = "性别为空")
    private Integer sex;
}
