package com.junho.productmgnt.common.response;

import org.springframework.stereotype.Service;

@Service
public class BaseResponseService {
    public <T> BaseResponse createSuccessResponse(T data) {
        return BaseResponse.builder()
            .isSuccess(true)
            .result(data)
            .build();
    }

    public <T> BaseResponse createErrorResponse(String message) {
        return BaseResponse.builder()
            .isSuccess(false)
            .message(message)
            .build();
    }
}