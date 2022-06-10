package com.takaki.recruit.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.takaki.recruit.annotation.PageValid;
import lombok.Data;

/**
 * @author Takaki
 * @date 2022/6/10
 */
@Data
@PageValid
public class TransferPage {
    private Integer pageNo = 1;
    private Integer pageSize = 20;
}
