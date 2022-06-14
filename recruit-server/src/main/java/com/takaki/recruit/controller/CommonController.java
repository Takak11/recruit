package com.takaki.recruit.controller;

import cn.hutool.core.map.MapUtil;
import com.takaki.recruit.constant.RestResponse;
import com.takaki.recruit.entity.dto.resource.FileIdTransfer;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.mapper.SysResourceMapper;
import com.takaki.recruit.service.SysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author Takaki
 * @date 2022/6/14
 */
@Api(tags = "通用功能接口")
@RestController
@RequestMapping("/api/recruit/common")
public class CommonController {

    @Autowired
    private SysResourceService resourceService;

    @ApiOperation("上传文件到服务器")
    @PostMapping("/upload")
    public RestResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        return RestResponse.success(MapUtil.of("id", resourceService.fileUpload(file)));
    }

    @ApiOperation("从服务器下载文件，获取文件src")
    @PostMapping("/download")
    public RestResponse download(@RequestBody @Validated FileIdTransfer fileId) throws BusinessBaseException {
         return RestResponse.success(MapUtil.of("src", resourceService.getFilePath(fileId)));
    }
}
