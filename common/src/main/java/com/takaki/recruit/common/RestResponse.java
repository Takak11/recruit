package com.takaki.recruit.common;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Takaki
 * @date 2022/6/10
 */
@Data
@ApiModel("返回值状态规范")
public class RestResponse {
    private Integer code;
    private String message;
    private Object data;

    public void setRestResponseState(ResponseStateEnum state) {
        this.code = state.getCode();
        this.message = state.getMessage();
    }

    public static RestResponse success() {
        RestResponse response = new RestResponse();
        response.setRestResponseState(ResponseStateEnum.SUCCESS);

        return response;
    }
    public static RestResponse success(Object data) {
        RestResponse response = new RestResponse();
        response.setData(data);
        response.setRestResponseState(ResponseStateEnum.SUCCESS);

        return response;
    }
    public static RestResponse fail(ResponseStateEnum state, Object data) {
        RestResponse response = new RestResponse();
        response.setRestResponseState(state);
        response.setData(data);

        return response;
    }
    public static RestResponse fail(ResponseStateEnum state) {
        RestResponse response = new RestResponse();
        response.setRestResponseState(state);

        return response;
    }
}
