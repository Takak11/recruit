package com.takaki.recruit.entity.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Takaki
 * @date 2022/6/10
 */
@Data
public class Dictionary {
    private String title;
    private String desc;
    private List<DictionaryItem> items;
}
