package com.takaki.recruit.entity.vo;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author Takaki
 * @date 2022/6/17
 */
@Data
public class UserPageInfo {

    private Integer id;
    private String username;
    private String name;
    private String avatar;
    private String mail;
    private String mobile;
    private Integer sex;
    private Integer age;
    private String createTime;
    private String updateTime;
}
