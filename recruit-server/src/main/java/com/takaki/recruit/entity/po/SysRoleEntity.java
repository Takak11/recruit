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
  @TableName("t_sys_role")
@ApiModel(value = "SysRoleEntity对象", description = "")
public class SysRoleEntity extends BaseEntity {

      @ApiModelProperty("主键")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("角色名称")
      @TableField("name")
    private String name;

      @ApiModelProperty("描述")
      @TableField("desc")
    private String desc;

      @ApiModelProperty("是否删除，0未删除，1删除")
      @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
