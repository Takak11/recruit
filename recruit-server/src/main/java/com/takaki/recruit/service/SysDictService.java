package com.takaki.recruit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.takaki.recruit.common.BasePageReturnType;
import com.takaki.recruit.common.TransferPage;
import com.takaki.recruit.entity.vo.Dictionary;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Takaki
 * @since 2022-06-09
 */
public interface SysDictService {
    /**
     * 获取系统数据字典
     * @param page 分页参数
     * @return 系统数据字典
     */
    BasePageReturnType<Dictionary> getSysDictionary(TransferPage page);
}
