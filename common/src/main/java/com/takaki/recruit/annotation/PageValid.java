package com.takaki.recruit.annotation;

import com.takaki.recruit.validator.PaginationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Takaki
 * @date 2022/6/10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = PaginationValidator.class)
public @interface PageValid {
    String message() default "分页参数范围错误";

    Class<?>[] group() default {};
    Class<? extends Payload>[] payload() default {};
}
