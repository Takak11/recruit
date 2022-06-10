package com.takaki.recruit.validator;

import com.takaki.recruit.annotation.PageValid;
import com.takaki.recruit.common.TransferPage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Takaki
 * @date 2022/6/10
 */
public class PaginationValidator implements ConstraintValidator<PageValid, TransferPage> {
    @Override
    public boolean isValid(TransferPage transferPage, ConstraintValidatorContext constraintValidatorContext) {
        return transferPage.getPageNo() >= 0 && transferPage.getPageSize() >= 0;
    }
}
