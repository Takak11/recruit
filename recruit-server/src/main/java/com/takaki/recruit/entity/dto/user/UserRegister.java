package com.takaki.recruit.entity.dto.user;

import com.takaki.recruit.entity.dto.mail.MailVerifier;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author Takaki
 * @date 2022/6/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRegister extends MailVerifier {
    @NotBlank(message = "手机号不能为空")
    private String mobile;
    @NotBlank(message = "昵称不能为空")
    private String name;
    @NotBlank(message = "密码不能为空")
    private String password;
}
