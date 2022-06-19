package com.takaki.recruit.service;

import com.takaki.recruit.entity.dto.user.UserId;
import com.takaki.recruit.exception.BusinessBaseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Takaki
 * @date 2022/6/19
 */
public interface ResumeService {
    /**
     * 添加简历
     * @param file
     * @return
     * @throws IOException
     * @throws BusinessBaseException
     */
    Boolean resumeAdd(MultipartFile file) throws IOException, BusinessBaseException;

    /**
     * 获取用户简历ID
     * @param id
     * @return
     * @throws BusinessBaseException
     */
    Integer getResumeId(Integer id) throws BusinessBaseException;

}
