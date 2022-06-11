package com.takaki.recruit.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Takaki
 * @date 2022/6/10
 */
@Data
public class BasePageReturnType<T> {
    @ApiModelProperty("总数")
    private Long total;
    @ApiModelProperty("记录")
    private List<T> records;
}
