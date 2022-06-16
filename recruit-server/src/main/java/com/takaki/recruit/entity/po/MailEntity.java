package com.takaki.recruit.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.takaki.recruit.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Takaki
 * @date 2022/6/15
 */
@Data
@ApiModel("邮箱验证码实体类")
@TableName("t_mail_code")
@EqualsAndHashCode(callSuper = true)
public class MailEntity extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String code;
    private String email;
}