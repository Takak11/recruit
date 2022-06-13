package com.takaki.recruit.handler;

import com.takaki.recruit.constant.ResponseStateEnum;
import com.takaki.recruit.constant.RestResponse;
import com.takaki.recruit.exception.BusinessBaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Takaki
 * @date 2022/6/10
 */
@Slf4j
@Order(-1)
@ControllerAdvice
public class GlobalExceptionHandler {

    @Order(-1)
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(BusinessBaseException.class)
    public RestResponse handleBaseException(BusinessBaseException e) {
        e.printStackTrace();
        log.error("业务逻辑异常：{}", e.getMessage());
        return RestResponse.fail(e.getCode(), e.getMessage());
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse handleValidationException(MethodArgumentNotValidException e) {
        e.printStackTrace();

        List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> (DefaultMessageSourceResolvable) error)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        log.error("参数校验错误{}", errors);

        return RestResponse.fail(
                ResponseStateEnum.ILLEGAL_ARGUMENT, errors
        );
    }

//    @ResponseBody
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(ExpiredJwtException.class)
//    public RestResponse handleExpiredException(ExpiredJwtException e) {
//        e.printStackTrace();
//        log.error("用户token过期：{}", e.getMessage());
//        return RestResponse.fail(ResponseStateEnum.UNAUTHORIZED);
//    }
}
