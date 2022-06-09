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
  @TableName("t_position")
@ApiModel(value = "PositionEntity对象", description = "")
public class PositionEntity extends BaseEntity {

      @ApiModelProperty("主键")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("工作名称")
      @TableField("name")
    private String name;

      @ApiModelProperty("工资")
      @TableField("salary")
    private String salary;

      @ApiModelProperty("工作职责")
      @TableField("mission")
    private String mission;

      @ApiModelProperty("类型")
      @TableField("code")
    private String code;

      @ApiModelProperty("父类型，-1表示无父类型")
      @TableField("p_code")
    private String pCode;

      @ApiModelProperty("工作要求")
      @TableField("requirement")
    private String requirement;

      @ApiModelProperty("招聘人数")
      @TableField("hire_num")
    private Integer hireNum;

      @ApiModelProperty("所属公司")
      @TableField("company_id")
    private Integer companyId;

      @ApiModelProperty("是否删除，0未删除，1删除")
      @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
