package com.junho.productmgnt.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException{
    private BaseExceptionStatus status;

    public BaseException(BaseExceptionStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
