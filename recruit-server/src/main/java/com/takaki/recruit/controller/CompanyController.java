package com.takaki.recruit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Takaki
 * @since 2022-06-09
 */
@Api(tags = "公司管理相关接口")
@RestController
@RequestMapping("/recruit/company-entity")
public class CompanyController {

    @PostMapping("/test")
    @ApiOperation(value = "测试1")
    public void testSwagger(@RequestBody @Validated String test) {
        System.out.println("111");
    }
    @PostMapping("/test2")
    public void testSwagger2() {
        System.out.println("222");
    }
}
