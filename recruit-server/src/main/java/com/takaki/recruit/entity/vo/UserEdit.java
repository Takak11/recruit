package com.takaki.recruit.entity.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Takaki
 * @date 2022/6/18
 */
@Data
@RequiredArgsConstructor
public class UserEdit {
    @NotBlank
    private String username;
    @NotBlank
    private String name;
    @NotNull
    private Integer sex;
    @NotNull
    private Integer age;
    @NotBlank
    private String mail;
    @NotBlank
    private String mobile;
}
