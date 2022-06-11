package com.takaki.recruit.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;

/**
 * @author Takaki
 * @date 2022/6/10
 */
@Data
public class TransferPage {
    @ApiModelProperty("页码")
    @Positive(message = "pageNo必须为正数")
    private Integer pageNo;
    @ApiModelProperty("页数")
    @Positive(message = "pageSize必须为正数")
    private Integer pageSize;

    public Integer getPageNo() {
        if (this.pageNo == null) {
            this.pageNo = 1;
        }
        return this.pageNo;
    }
    public Integer getPageSize() {
        if (this.pageSize == null) {
            this.pageSize = 20;
        }
        return this.pageSize;
    }
}
