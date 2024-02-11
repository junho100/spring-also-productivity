package com.junho.productmgnt.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseExceptionStatus {
    FIREBASE_MESSAGING_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "firebase messaging exception"),
    AUTH_ADMIN_NOT_FOUND(HttpStatus.BAD_REQUEST, "admin not found"),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "admin not found"),
    ADMIN_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "admin already exists"),
    OAUTH2_USER_EXISTS(HttpStatus.BAD_REQUEST, "user already exists"),
    OAUTH2_EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "oauth2 email not found"),
    USER_EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "user email not found"),
    INVALID_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "invalid oauth provider"),
    INVALID_AUTH(HttpStatus.UNAUTHORIZED, "invalid authentication"),
    USER_EXISTS(HttpStatus.BAD_REQUEST, "user already exists"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user not found"),
    PRODUCT_AUDIT_NOT_FOUND(HttpStatus.NOT_FOUND, "product audit not found."),
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
