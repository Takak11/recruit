package com.takaki.recruit.common;

import lombok.Data;

import java.util.List;

/**
 * @author Takaki
 * @date 2022/6/10
 */
@Data
public class BasePageReturnType<T> {
    private Long total;
    private List<T> records;
}
