package com.takaki.recruit.entity.vo;

import lombok.Data;

/**
 * @author Takaki
 * @date 2022/6/12
 */
@Data
public class UserInfo {
    private String username;
    private String name;
    private String avatar;
    private String mail;
    private Integer age;
    private String mobile;
    private Integer sex;
    private String expiredDate;
}
