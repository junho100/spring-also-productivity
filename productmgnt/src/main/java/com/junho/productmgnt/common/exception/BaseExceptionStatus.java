package com.junho.productmgnt.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseExceptionStatus {
    INVALID_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "invalid oauth provider"),
    INVALID_AUTH(HttpStatus.UNAUTHORIZED, "invalid authentication"),
    USER_EXISTS(HttpStatus.BAD_REQUEST, "user already exists"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user not found"),
    PRODUCT_AUDIT_NOT_FOUND(HttpStatus.NOT_FOUND, "project audit not found."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "category not found."),
    SIGNIN_FAILED(HttpStatus.UNAUTHORIZED, "login failed."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "product not found.");
    private final HttpStatus status;
    private final String message;

    private BaseExceptionStatus(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
