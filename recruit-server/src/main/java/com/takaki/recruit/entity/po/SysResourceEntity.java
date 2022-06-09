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
  @TableName("t_sys_resource")
@ApiModel(value = "SysResourceEntity对象", description = "")
public class SysResourceEntity extends BaseEntity {

      @ApiModelProperty("主键")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("文件路径")
      @TableField("path")
    private String path;

      @ApiModelProperty("文件类型")
      @TableField("type_code")
    private String typeCode;

      @ApiModelProperty("文件名")
      @TableField("file_name")
    private String fileName;

      @ApiModelProperty("删除标志，0未删除，1删除")
      @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
