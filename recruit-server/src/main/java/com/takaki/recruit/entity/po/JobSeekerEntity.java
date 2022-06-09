package com.takaki.recruit.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.takaki.recruit.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Takaki
 * @since 2022-06-09
 */
@Getter
@Setter
  @TableName("t_job_seeker")
@ApiModel(value = "JobSeekerEntity对象", description = "")
public class JobSeekerEntity extends BaseEntity {

      @ApiModelProperty("主键")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("名称")
      @TableField("name")
    private String name;

      @ApiModelProperty("自我介绍")
      @TableField("intro")
    private String intro;

      @ApiModelProperty("简历")
      @TableField("resume")
    private Integer resume;

      @ApiModelProperty("用户的类型（0求职者、1HR）")
      @TableField("status")
    private Boolean status;

      @ApiModelProperty("需求id")
      @TableField("demand_id")
    private Integer demandId;

      @ApiModelProperty("用户id")
      @TableField("user_id")
    private Integer userId;

      @ApiModelProperty("是否删除，0未删除，1删除")
      @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
