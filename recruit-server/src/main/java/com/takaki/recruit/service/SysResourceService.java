package com.takaki.recruit.service;

import com.takaki.recruit.entity.dto.resource.FileIdTransfer;
import com.takaki.recruit.entity.po.SysResourceEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.takaki.recruit.exception.BusinessBaseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Takaki
 * @since 2022-06-09
 */
public interface SysResourceService extends IService<SysResourceEntity> {

    /**
     * 插入文件到数据表，存储文件，并返回文件表中的主键信息
     * @param file 需要上传的文件
     * @return 插入之后文件的ID
     * @throws IOException 内部错误
     */
    Integer fileUpload(MultipartFile file) throws IOException;


    /**
     * 从数据表中获取文件src
     * @param fileId 文件id
     * @return src
     * @throws BusinessBaseException 当文件不存在时
     */
    String getFilePath(FileIdTransfer fileId) throws BusinessBaseException;
}
