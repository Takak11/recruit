package com.takaki.recruit.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import com.takaki.recruit.constant.ResponseStateConstant;
import com.takaki.recruit.constant.RestResponse;
import com.takaki.recruit.entity.dto.mail.MailReceiver;
import com.takaki.recruit.entity.dto.resource.FileIdTransfer;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.service.MailSenderService;
import com.takaki.recruit.service.ResumeService;
import com.takaki.recruit.service.SysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Takaki
 * @date 2022/6/14
 */
@Slf4j
@Api(tags = "通用功能接口")
@RestController
@RequestMapping("/api/recruit/common")
public class CommonController {

    @Autowired
    private SysResourceService resourceService;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private ResumeService resumeService;

    @Value("${common.default-resume}")
    private Integer defaultResume;

    @ApiOperation("获取用户简历")
    @GetMapping("/resume/{id}")
    public void getUserResume(@PathVariable("id") Integer id, HttpServletResponse response) throws BusinessBaseException, IOException {
        Integer resumeId = resumeService.getResumeId(id);

        if (!ObjectUtils.isEmpty(resumeId)) {
            this.download(resumeId, response);
        } else {
            this.download(defaultResume, response);
        }
    }

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
    public RestResponse sendVerificationCode(@RequestBody @Validated MailReceiver receiver) {

        log.info("收件人邮箱：{}", receiver.getMail());
        String code = mailSenderService.sendCode(receiver);

        return code == null
                ? RestResponse.fail(ResponseStateConstant.ERROR_CODE, "服务器内部错误")
                : RestResponse.success(MapUtil.of("sms", code));
    }
}
