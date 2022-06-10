package com.takaki.recruit.constant;

import java.util.Arrays;

/**
 * @author Takaki
 * @date 2022/6/10
 *
 */
public enum ResponseStateEnum {
    /**
     * 见各枚举状态message字段
     */
    SUCCESS(200, "成功"),
    ILLEGAL_ARGUMENT(400, "参数错误"),
    UNAUTHORIZED(401, "未登录/授权的操作"),
    NOT_FOUND(404, "服务不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    BUSINESS_LOGIC_ERROR(600, "业务逻辑错误");

    private final Integer code;
    private final String message;

    ResponseStateEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCodeByName(String name) {

        return Arrays.stream(ResponseStateEnum.values())
                .filter(response -> response.getMessage().equals(name))
                .findFirst()
                .map(ResponseStateEnum::getCode)
                .orElse(null);
    }

    @Override
    public String toString() {
        return this.name();
    }
}
