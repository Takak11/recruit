package com.takaki.recruit.entity.dto.resource;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Takaki
 * @date 2022/6/14
 */
@Data
public class FileIdTransfer {
    @NotNull(message = "id为空")
    private Integer id;
}
