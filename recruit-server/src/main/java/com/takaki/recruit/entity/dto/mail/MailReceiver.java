package com.takaki.recruit.entity.dto.mail;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Takaki
 * @date 2022/6/15
 */
@Data
public class MailReceiver {
    @NotBlank(message = "邮箱账号不能为空")
    private String mail;
}
