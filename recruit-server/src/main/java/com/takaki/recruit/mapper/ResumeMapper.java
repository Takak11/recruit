package com.takaki.recruit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.takaki.recruit.entity.po.ResumeRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Takaki
 * @date 2022/6/19
 */
@Mapper
public interface ResumeMapper extends BaseMapper<ResumeRelationEntity> {
}
