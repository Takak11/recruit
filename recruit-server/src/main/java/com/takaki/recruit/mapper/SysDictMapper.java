package com.takaki.recruit.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.takaki.recruit.entity.vo.Dictionary;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Takaki
 * @since 2022-06-09
 */
@Mapper
public interface SysDictMapper {
    /**
     * 获取系统的数据字典及其表项
     * @param page 分页查询参数
     * @return 字典列表
     */
    IPage<Dictionary> getSysDictionary(IPage<Dictionary> page);
}
