package com.takaki.recruit.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.takaki.recruit.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Takaki
 * @date 2022/6/13
 */
@Slf4j
@Component
public class FieldAutoFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("调用字段INSERT填充");
        this.strictInsertFill(metaObject, "createTime", Date::new, Date.class);
        this.strictInsertFill(metaObject, "updateTime", Date::new, Date.class);
        Object principal = SecurityUtils.getSubject().getPrincipal();


        if (null != principal) {

            String token = principal.toString();
            this.strictInsertFill(
                    metaObject,
                    "createdBy",
                    () -> JwtUtil.getUsernameFromToken(token),
                    String.class
            );
            this.strictInsertFill(
                    metaObject,
                    "updatedBy",
                    () -> JwtUtil.getUsernameFromToken(token),
                    String.class
            );
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("调用字段UPDATE填充");
        this.strictInsertFill(metaObject, "updateTime", Date::new, Date.class);
        Object principal = SecurityUtils.getSubject().getPrincipal();


        if (null != principal) {
            String token = principal.toString();
            this.strictInsertFill(
                    metaObject,
                    "updatedBy",
                    () -> JwtUtil.getUsernameFromToken(token),
                    String.class
            );
        }
    }
}
