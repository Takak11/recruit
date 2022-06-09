package com.takaki.recruit.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Takaki
 * @date 2022/6/9
 */
@ApiModel("公共字段父类")
@Data
public class BaseEntity {
    @TableField(value = "create_time", fill = FieldFill.INSERT_UPDATE)
    private String createTime;
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private String updateTime;
    @TableField(value = "created_by", fill = FieldFill.INSERT_UPDATE)
    private String createdBy;
    @TableField(value = "created_by", fill = FieldFill.UPDATE)
    private String updatedBy;
}
