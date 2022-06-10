package com.takaki.recruit.controller;

import com.takaki.recruit.common.BasePageReturnType;
import com.takaki.recruit.constant.RestResponse;
import com.takaki.recruit.common.TransferPage;
import com.takaki.recruit.entity.vo.Dictionary;
import com.takaki.recruit.service.SysDictService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Takaki
 * @since 2022-06-09
 */
@RestController
@Api(tags = {"数据字典管理"})
@RequestMapping("/api/recruit")
public class SysDictController {

    @Autowired
    private SysDictService service;

    @PostMapping("/dict")
    public RestResponse getSysDict(@RequestBody @Validated TransferPage page) {

        BasePageReturnType<Dictionary> result = service.getSysDictionary(page);
        result.setTotal(result.getTotal());
        result.setRecords(result.getRecords());

        return RestResponse.success(result);
    }
}
