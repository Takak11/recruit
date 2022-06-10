package com.takaki.recruit.handler;

import com.takaki.recruit.constant.ResponseStateEnum;
import com.takaki.recruit.constant.RestResponse;
import com.takaki.recruit.exception.BusinessBaseException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Takaki
 * @date 2022/6/10
 */
@Slf4j
@Order(-1)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(BusinessBaseException.class)
    public RestResponse handleBaseException(BusinessBaseException e) {
        e.printStackTrace();
        log.error("业务逻辑异常：{}", e.getMessage());
        return RestResponse.fail(ResponseStateEnum.BUSINESS_LOGIC_ERROR);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public RestResponse handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        log.error("服务器内部异常：{}", e.getMessage());
        return RestResponse.fail(ResponseStateEnum.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public RestResponse handleExpiredException(ExpiredJwtException e) {
        e.printStackTrace();
        log.error("用户token过期：{}", e.getMessage());
        return RestResponse.fail(ResponseStateEnum.UNAUTHORIZED);
    }
}
