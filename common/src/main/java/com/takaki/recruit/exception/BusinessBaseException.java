package com.takaki.recruit.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Takaki
 * @date 2022/6/10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessBaseException extends Exception {

    private Integer code;
    private String message;
    public BusinessBaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}

