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
  @TableName("t_user_role_relation")
@ApiModel(value = "UserRoleRelationEntity对象", description = "")
public class UserRoleRelationEntity extends BaseEntity {

      @ApiModelProperty("主键")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户主键")
      @TableField("user_id")
    private Integer userId;

      @ApiModelProperty("角色主键")
      @TableField("role_id")
    private Integer roleId;

      @ApiModelProperty("是否删除，0未删除，1删除")
      @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
