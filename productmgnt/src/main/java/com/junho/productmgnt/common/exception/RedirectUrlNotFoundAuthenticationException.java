package com.junho.productmgnt.common.exception;

import org.springframework.security.core.AuthenticationException;

public class RedirectUrlNotFoundAuthenticationException extends AuthenticationException {
    public RedirectUrlNotFoundAuthenticationException(String msg) {
        super(msg);
    }
}
