package com.takaki.recruit.service.impl;

import cn.hutool.core.map.MapUtil;
import com.takaki.recruit.constant.ResponseStateConstant;
import com.takaki.recruit.entity.dto.user.UserId;
import com.takaki.recruit.entity.po.ResumeRelationEntity;
import com.takaki.recruit.entity.po.SysUserEntity;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.mapper.ResumeMapper;
import com.takaki.recruit.service.ResumeService;
import com.takaki.recruit.service.SysResourceService;
import com.takaki.recruit.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Takaki
 * @date 2022/6/19
 */
@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private SysResourceService service;
    @Autowired
    private ResumeMapper mapper;
    @Autowired
    private SysUserService userService;

    private void updateResume(ResumeRelationEntity entity, Integer id, Integer resumeId) {
        entity.setUserId(id);
        entity.setResumeId(resumeId);
    }

    @Override
    @Transactional(rollbackFor = {IOException.class, BusinessBaseException.class})
    public Boolean resumeAdd(MultipartFile file) throws IOException, BusinessBaseException {
        Integer id = service.fileUpload(file);
        SysUserEntity userEntity = userService.getUserEntity();

        List<ResumeRelationEntity> resumeList = mapper.selectByMap(MapUtil.of("user_id", userEntity.getId()));


        if (resumeList.size() != 1) {
            ResumeRelationEntity entity = new ResumeRelationEntity();
            this.updateResume(entity, userEntity.getId(), id);
            resumeList.forEach(resume -> mapper.deleteById(resume.getId()));
            mapper.insert(entity);
        } else {
            ResumeRelationEntity resumeEntity = resumeList.get(0);
            this.updateResume(resumeEntity, userEntity.getId(), id);
            mapper.updateById(resumeEntity);
        }

        return true;
    }

    @Override
    public Integer getResumeId(Integer id) throws BusinessBaseException {

        List<ResumeRelationEntity> resumes = mapper.selectByMap(MapUtil.of("user_id", id));

        if(resumes.size() == 0) {
            return null;
        }
        if (resumes.size() > 1) {
            throw new BusinessBaseException(ResponseStateConstant.ERROR_CODE, "用户简历不存在");
        }

        ResumeRelationEntity realResume = resumes.get(0);
        return realResume.getResumeId();
    }


}
