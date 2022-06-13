package com.takaki.recruit.entity.dto.user;

import com.takaki.recruit.entity.vo.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author Takaki
 * @date 2022/6/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRegister extends UserTransfer {
    @NotBlank(message = "密码不能为空")
    private String password;
}
