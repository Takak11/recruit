package com.takaki.recruit.entity.vo;

import lombok.Data;

/**
 * @author Takaki
 * @date 2022/6/17
 */
@Data
public class UserDetail {
    private String name;
    private String username;
    private String mail;
    private String mobile;
    private Integer age;
    private Integer sex;
}
