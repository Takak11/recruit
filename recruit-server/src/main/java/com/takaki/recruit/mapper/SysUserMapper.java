package com.takaki.recruit.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.takaki.recruit.entity.dto.user.UserListPage;
import com.takaki.recruit.entity.po.SysUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.takaki.recruit.entity.vo.UserPageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Takaki
 * @since 2022-06-09
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity> {
    /**
     * 用户列表分页查询
     * @param page
     * @return
     */
    IPage<UserPageInfo> getUserPage(IPage<UserPageInfo> page, @Param("additionalProps") UserListPage additionalProps);
}
