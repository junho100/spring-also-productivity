package com.junho.productmgnt.common.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BaseResponse<T> {
    private Boolean isSuccess;
    private T result;
    private String message;
}
