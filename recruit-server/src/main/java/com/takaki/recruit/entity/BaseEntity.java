package com.takaki.recruit.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author Takaki
 * @date 2022/6/9
 */
@ApiModel("公共字段父类")
@Data
public class BaseEntity {
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;
}
