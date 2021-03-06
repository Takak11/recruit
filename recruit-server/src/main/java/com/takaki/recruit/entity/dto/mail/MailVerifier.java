package com.takaki.recruit.entity.dto.mail;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Takaki
 * @date 2022/6/17
 */
@Data
public class MailVerifier {
    @NotBlank(message = "邮箱号不能为空")
    private String mail;
    @NotBlank(message = "验证码不能为空")
    private String sms;
}
