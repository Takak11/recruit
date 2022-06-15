package com.takaki.recruit.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.UUID;
import com.takaki.recruit.constant.ResponseStateConstant;
import com.takaki.recruit.entity.dto.resource.FileIdTransfer;
import com.takaki.recruit.entity.po.SysResourceEntity;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.mapper.SysResourceMapper;
import com.takaki.recruit.service.SysResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.takaki.recruit.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Takaki
 * @since 2022-06-09
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResourceEntity> implements SysResourceService {

    @Value("${common.file-path}")
    private String filePath;

    @Autowired
    private SysResourceMapper resourceMapper;

    @Override
    public Integer fileUpload(MultipartFile file) throws IOException {

        String dirPath = filePath
                + File.separator
                + file.getContentType();
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String path = dirPath + File.separator
                        + UUID.fastUUID().toString()
                        + file.getOriginalFilename();

        File store = new File(path);

        IoUtil.copy(file.getInputStream(), new FileOutputStream(store));

        SysResourceEntity entity = new SysResourceEntity();
        entity.setFileName(file.getOriginalFilename());
        entity.setPath(path);
        entity.setTypeCode(file.getContentType());

        resourceMapper.insert(entity);

        return entity.getId();
    }

    @Override
    public String getFilePath(FileIdTransfer fileId) throws BusinessBaseException {

        SysResourceEntity entity = resourceMapper.selectById(fileId.getId());
        if (null == entity) {
            throw new BusinessBaseException(ResponseStateConstant.ERROR_CODE, "给定文件ID不存在");
        }

        return entity.getPath();
    }
}
