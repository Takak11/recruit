package com.takaki.recruit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Takaki
 * @date 2022/6/11
 */
@Api(tags = {"全局处理控制器"})
@RestController
@RequestMapping("/api/recruit/global")
public class GlobalController {

    @ApiOperation(value = "用于接收Filter抛出的异常(禁主动调用)")
    @RequestMapping("/error")
    public void errorThrow(HttpServletRequest request) throws Exception {
        throw ((Exception) request.getAttribute("filter.e"));
    }
}
