package com.takaki.recruit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.takaki.recruit.common.BasePageReturnType;
import com.takaki.recruit.common.TransferPage;
import com.takaki.recruit.constant.ResponseStateConstant;
import com.takaki.recruit.constant.ResponseStateEnum;
import com.takaki.recruit.entity.dto.user.*;
import com.takaki.recruit.entity.po.MailEntity;
import com.takaki.recruit.entity.po.SysResourceEntity;
import com.takaki.recruit.entity.po.SysUserEntity;
import com.takaki.recruit.entity.vo.UserEdit;
import com.takaki.recruit.entity.vo.UserInfo;
import com.takaki.recruit.entity.vo.UserPageInfo;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.mapper.MailMapper;
import com.takaki.recruit.mapper.SysUserMapper;
import com.takaki.recruit.service.SysResourceService;
import com.takaki.recruit.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.takaki.recruit.utils.JwtUtil;
import com.takaki.recruit.utils.MyBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Takaki
 * @since 2022-06-09
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private MailMapper mailMapper;
    @Autowired
    private SysResourceService resourceService;

    @Override
    public SysUserEntity getUserEntity() throws BusinessBaseException {

        String token = SecurityUtils.getSubject().getPrincipal().toString();
        String username = JwtUtil.getUsernameFromToken(token);

        log.info("{} ==> 查询用户", this.getClass());
        List<SysUserEntity> users = userMapper.selectByMap(MapUtil.of("username", username));
        int size = users.size();
        if (size != 1) {
            throw new BusinessBaseException(ResponseStateEnum.ILLEGAL_ARGUMENT.getCode(), "用户信息不存在");
        }
        return users.get(0);
    }

    @Override
    public UserInfo getUserInfo() throws BusinessBaseException {
        UserInfo userInfo = new UserInfo();

        SysUserEntity user = this.getUserEntity();
        BeanUtil.copyProperties(user, userInfo, true);
        String expiredDate = this.getExpiredDate();
        userInfo.setExpiredDate(expiredDate);

        return userInfo;
    }

    @Override
    public String getExpiredDate() {

        String token = SecurityUtils
                .getSubject()
                .getPrincipal()
                .toString();

        Date expiredDate = JwtUtil.getExpiredDate(token);
        return DateUtil.format(expiredDate, "yyyy-MM-dd HH:mm:ss");

    }

    @Override
    public boolean renewPassword(UserPassword userPassword) throws BusinessBaseException {
        if (userPassword.getCurrentPassword().equals(userPassword.getNewPassword())) {
            throw new BusinessBaseException(ResponseStateConstant.ERROR_CODE, "两次密码相同，请修改后重试");
        }

        SysUserEntity userEntity = this.getUserEntity();
        String crypt = userEntity.getPassword();
        boolean isLegal = BCrypt.checkpw(userPassword.getCurrentPassword(), crypt);
        if (!isLegal) {
            throw new BusinessBaseException(ResponseStateEnum.ILLEGAL_ARGUMENT.getCode(), "旧密码错误");
        }

        userEntity.setPassword(BCrypt.hashpw(userPassword.getNewPassword()));
        userMapper.updateById(userEntity);

        return true;
    }

    @Override
    public boolean updateUserInfo(UserTransfer userInfo) throws BusinessBaseException {

        log.info("调用updateUserInfoApi");
        SysUserEntity userEntity = this.getUserEntity();
        log.info("dto信息{}", userInfo);
        if (MyBeanUtil.isCommonFieldsValueEqual(userInfo, userEntity)) {
            return false;
        }

        BeanUtil.copyProperties(
                userInfo,
                userEntity,
                CopyOptions.create().ignoreNullValue()
        );
        userMapper.updateById(userEntity);

        return true;
    }

    private String getUsername() {
        String username;
        while (true) {
            username = RandomUtil.randomNumbers(10);
            List<SysUserEntity> entity = userMapper.selectByMap(MapUtil.of("username", username));
            if (entity.size() == 0) {
                break;
            }
        }
        return username;
    }
    private void encryptPassword(String rawPassword, SysUserEntity entity) {
        String password = BCrypt.hashpw(rawPassword);

        entity.setPassword(password);
    }

    @Override
    public String register(UserRegister info) throws BusinessBaseException {

        List<SysUserEntity> userList = userMapper.selectByMap(MapUtil.of("mail", info.getMail()));
        if (userList.size() != 0) {
            throw new BusinessBaseException(ResponseStateConstant.ERROR_CODE, "邮箱号已被注册");
        }

        List<MailEntity> mailEntities = mailMapper.selectList(
                new QueryWrapper<MailEntity>()
                        .eq("email", info.getMail())
                        .eq("code", info.getSms())
        );
        if (mailEntities.size() != 1) {
            throw new BusinessBaseException(ResponseStateConstant.ERROR_CODE, "验证码错误");
        }

        SysUserEntity userEntity = new SysUserEntity();
        BeanUtil.copyProperties(info, userEntity, true);

        this.encryptPassword(info.getPassword(), userEntity);
        String username = this.getUsername();
        userEntity.setUsername(username);
        userMapper.insert(userEntity);
        return username;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateUserAvatar(MultipartFile file) throws IOException, BusinessBaseException {

        Integer id = resourceService.fileUpload(file);
        SysUserEntity userEntity = null;

        if (null != id) {

            userEntity = this.getUserEntity();
            userEntity.setAvatar(id);

            userMapper.updateById(userEntity);
            SysResourceEntity resource = resourceService.getById(id);

            return resource.getPath();
        }

        return null;
    }

    @Override
    public BasePageReturnType<UserPageInfo> getUserList(UserListPage page) {

        IPage<UserPageInfo> userPage = userMapper.getUserPage(
                new Page<>(
                        page.getPageNo(),
                        page.getPageSize()
                )
                ,page
        );

        BasePageReturnType<UserPageInfo> returnType = new BasePageReturnType<>();
        returnType.setTotal(userPage.getTotal());
        returnType.setRecords(userPage.getRecords());

        return returnType;
    }

    @Override
    public Boolean deleteUser(UserId id) {
        int affectedLines = userMapper.deleteById(id.getId());

        return affectedLines != 0;
    }

    @Override
    public Boolean editUser(UserEdit edit) throws BusinessBaseException {

        String username = edit.getUsername();
        List<SysUserEntity> entityList = userMapper.selectByMap(MapUtil.of("username", username));
        if (entityList.size() != 1) {
            throw new BusinessBaseException(ResponseStateConstant.ERROR_CODE, "用户不存在");
        }

        SysUserEntity entity = entityList.get(0);

        BeanUtil.copyProperties(edit, entity);
        userMapper.updateById(entity);

        return true;
    }

    @Override
    public String addUser(UserAdd user) {

        SysUserEntity entity = new SysUserEntity();
        BeanUtil.copyProperties(user, entity);

        String username = this.getUsername();
        entity.setUsername(username);
        this.encryptPassword(user.getPassword(), entity);

        userMapper.insert(entity);

        return username;
    }

}
