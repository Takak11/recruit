package com.takaki.recruit.entity.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Takaki
 * @date 2022/6/17
 */
@Data
@RequiredArgsConstructor
public class UserId {
    @NotNull(message = "用户ID不能为空")
    private Integer id;
}
