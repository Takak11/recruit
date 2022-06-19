package com.takaki.recruit.controller;

import cn.hutool.core.map.MapUtil;
import com.takaki.recruit.constant.RestResponse;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.service.ResumeService;
import com.takaki.recruit.service.SysResourceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Takaki
 * @date 2022/6/19
 */
@RestController
@RequestMapping("/api/recruit/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;


    @PostMapping("/add")
    @ApiOperation("添加简历")
    public RestResponse addResume(@RequestParam("file") MultipartFile file) throws IOException, BusinessBaseException {

        return RestResponse.success(MapUtil.of("result", resumeService.resumeAdd(file)));
    }
}
