package com.takaki.recruit.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.takaki.recruit.entity.dto.user.UserTransfer;
import com.takaki.recruit.entity.po.SysUserEntity;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Takaki
 * @date 2022/6/14
 */
public class MyBeanUtil {

    /**
     * 更新
     * @param source
     * @param target
     * @param ignoreFields
     * @return
     */
    public static boolean isCommonFieldsValueEqual(Object source, Object target, String... ignoreFields) {

        if (null == source && null == target) {
            return true;
        }
        if (null == source || null == target) {
            return false;
        }

        Map<String, Object> sourceFieldsMap = BeanUtil.beanToMap(source);
        Map<String, Object> targetFieldsMap = BeanUtil.beanToMap(target);

        Set<String> sourceFields = sourceFieldsMap.keySet();
        sourceFields.removeAll(Arrays.asList(ignoreFields));

        for (String field : sourceFields) {

            Object sourceValue = sourceFieldsMap.get(field);
            Object targetValue = targetFieldsMap.get(field);

            if (null == sourceValue && null == targetValue) {
                continue;
            }
            if (!sourceValue.equals(targetValue)) {
                return false;
            }
        }

        return true;

    }

    public static void main(String[] args) {
        UserTransfer userTransfer = new UserTransfer();
        SysUserEntity userEntity = new SysUserEntity();
        userTransfer.setAge(20);
        userTransfer.setMail("asdasd@qweqwe");
        userTransfer.setSex(1);
        userTransfer.setName("zczxc");
        userTransfer.setMobile("1313125353");

        BeanUtil.copyProperties(userTransfer, userEntity);
//        userEntity.setAge(11);
        System.out.println(isCommonFieldsValueEqual(userEntity, userTransfer));
    }
}
