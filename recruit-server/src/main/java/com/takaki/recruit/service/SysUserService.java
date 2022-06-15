package com.takaki.recruit.service;

import com.takaki.recruit.entity.dto.user.UserPassword;
import com.takaki.recruit.entity.dto.user.UserRegister;
import com.takaki.recruit.entity.dto.user.UserTransfer;
import com.takaki.recruit.entity.po.SysUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.takaki.recruit.entity.vo.UserInfo;
import com.takaki.recruit.exception.BusinessBaseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Takaki
 * @since 2022-06-09
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 获取环境中的Token并获取其用户名，根据用户名查询用户获取信息
     * @return 用户信息
     * @throws BusinessBaseException 用户信息不存在时或多于一个候选者
     */
    UserInfo getUserInfo() throws BusinessBaseException;

    /**
     * 获取token过期时间
     * @return token过期日期
     */
    String getExpiredDate();

    /**
     * 修改密码
     * @param userPassword
     * @return
     * @throws BusinessBaseException
     */
    boolean renewPassword(UserPassword userPassword) throws BusinessBaseException;


    /**
     * 更新用户信息
     * @param userInfo
     * @return 结果
     * @throws BusinessBaseException
     */
    boolean updateUserInfo(UserTransfer userInfo) throws BusinessBaseException;

    /**
     * 用户注册方法，成功时返回获得的账号
     * @param info
     * @return
     */
    String register(UserRegister info);

    /**
     * 上传用户头像
     * @param file 上传来的文件
     * @return 头像保存的路径
     * @throws IOException 文件创建错误
     * @throws BusinessBaseException 无此用户
     */
    String updateUserAvatar(MultipartFile file) throws IOException, BusinessBaseException;
}
