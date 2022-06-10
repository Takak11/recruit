package com.takaki.recruit.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.takaki.recruit.common.BasePageReturnType;
import com.takaki.recruit.common.TransferPage;
import com.takaki.recruit.entity.vo.Dictionary;
import com.takaki.recruit.mapper.SysDictMapper;
import com.takaki.recruit.service.SysDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Takaki
 * @since 2022-06-09
 */
@Service
public class SysDictServiceImpl implements SysDictService {

    @Resource
    private SysDictMapper mapper;

    @Override
    public BasePageReturnType<Dictionary> getSysDictionary(TransferPage page) {
        IPage<Dictionary> dictionaryPage = mapper.getSysDictionary(
                new Page<>(page.getPageNo(), page.getPageSize())
        );

        BasePageReturnType<Dictionary> pageResult = new BasePageReturnType<>();
        pageResult.setTotal(dictionaryPage.getTotal());
        pageResult.setRecords(dictionaryPage.getRecords());
        return pageResult;
    }
}
