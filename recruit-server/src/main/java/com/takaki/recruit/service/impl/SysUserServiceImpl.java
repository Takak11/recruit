package com.takaki.recruit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.takaki.recruit.constant.ResponseStateConstant;
import com.takaki.recruit.constant.ResponseStateEnum;
import com.takaki.recruit.entity.dto.user.UserPassword;
import com.takaki.recruit.entity.dto.user.UserRegister;
import com.takaki.recruit.entity.dto.user.UserTransfer;
import com.takaki.recruit.entity.po.SysUserEntity;
import com.takaki.recruit.entity.vo.UserInfo;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.mapper.SysUserMapper;
import com.takaki.recruit.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.takaki.recruit.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private SysUserEntity getUserEntity() throws BusinessBaseException {

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
    public void updateUserInfo(UserTransfer userInfo) throws BusinessBaseException {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        if (null == token) {
            throw new BusinessBaseException(ResponseStateConstant.ERROR_CODE, "请重新登录后重试");
        }

        String username = JwtUtil.getUsernameFromToken(token);
        List<SysUserEntity> user = userMapper.selectByMap(MapUtil.of("username", username));
        if (user.size() != 1) {
            throw new BusinessBaseException(ResponseStateConstant.ERROR_CODE, "用户状态错误");
        }

        SysUserEntity userEntity = user.get(0);

        BeanUtil.copyProperties(
                userInfo,
                userEntity,
                CopyOptions.create().ignoreNullValue()
        );
        userMapper.updateById(userEntity);
    }

    @Override
    public String register(UserRegister info) {
        String username;
        while (true) {
            username = RandomUtil.randomNumbers(10);
            List<SysUserEntity> entity = userMapper.selectByMap(MapUtil.of("username", username));
            if (entity.size() == 0) {
                break;
            }
        }

        SysUserEntity userEntity = new SysUserEntity();
        info.setPassword(BCrypt.hashpw(info.getPassword()));
        BeanUtil.copyProperties(info, userEntity, true);
        userEntity.setUsername(username);

        userMapper.insert(userEntity);
        return username;
    }


}
