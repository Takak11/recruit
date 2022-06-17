package com.takaki.recruit.entity.dto.user;

import com.takaki.recruit.entity.dto.mail.MailVerifier;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author Takaki
 * @date 2022/6/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResetPassword extends MailVerifier {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "新密码不能为空")
    private String password;
}
