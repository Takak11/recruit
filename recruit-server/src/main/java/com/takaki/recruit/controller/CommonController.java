package com.takaki.recruit.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import com.takaki.recruit.constant.RestResponse;
import com.takaki.recruit.entity.dto.mail.MailReceiver;
import com.takaki.recruit.entity.dto.resource.FileIdTransfer;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.service.MailSenderService;
import com.takaki.recruit.service.SysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

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
    @Autowired
    private MailSenderService mailSenderService;

    @ApiOperation("上传文件到服务器")
    @PostMapping("/upload")
    public RestResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        return RestResponse.success(MapUtil.of("id", resourceService.fileUpload(file)));
    }

    @ApiOperation("从服务器下载文件，RestFul格式")
    @GetMapping("/rest/download/{id}")
    public void download(@PathVariable("id") Integer fileId, HttpServletResponse response) throws BusinessBaseException, IOException {

        FileIdTransfer fileIdTransfer = new FileIdTransfer();
        fileIdTransfer.setId(fileId);

        String filePath = resourceService.getFilePath(fileIdTransfer);
        FileInputStream inputStream = new FileInputStream(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        IoUtil.copy(inputStream, outputStream);

    }

    @ApiOperation("发送邮件验证码")
    @PostMapping("/sms")
    public RestResponse sendVerificationCode(@RequestBody @Validated MailReceiver receiver) throws ExecutionException, InterruptedException {
        String code = mailSenderService.sendCode(receiver);

        return RestResponse.success(MapUtil.of("sms", code));
    }
}
