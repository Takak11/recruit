package com.takaki.recruit.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.takaki.recruit.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * @author Takaki
 * @date 2022/6/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@TableName("t_user_resume_relation")
public class ResumeRelationEntity extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("resume_id")
    private Integer resumeId;

    @TableLogic
    @TableField("is_deleted")
    private Boolean deleted;
}
