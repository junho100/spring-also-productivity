package com.junho.productmgnt.common.exception;

import com.junho.productmgnt.common.response.BaseResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionAdvice {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<Object>> handleBaseException(BaseException exception) {
        BaseResponse<Object> response = BaseResponse.builder()
            .isSuccess(false)
            .message(exception.getMessage())
            .build();
        return new ResponseEntity<>(response, exception.getStatus().getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationException(MethodArgumentNotValidException exception) {
        List<String> errorFields = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorFields.add(fieldError.getField());
        });
        String message = "invalid argument : " + errorFields;
        BaseResponse<Object> response = BaseResponse.builder()
            .isSuccess(false)
            .message(message)
            .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleAuthInfoException(UsernameNotFoundException exception) {
        BaseResponse<Object> response = BaseResponse.builder()
            .isSuccess(false)
            .message(exception.getMessage())
            .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<BaseResponse<Object>> handleOAuth2AuthenticationException(OAuth2AuthenticationException exception) {
        BaseResponse<Object> response = BaseResponse.builder()
            .isSuccess(false)
            .message(exception.getMessage())
            .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGeneralException(Exception exception) {
        BaseResponse<Object> response = BaseResponse.builder()
            .isSuccess(false)
            .message(exception.getMessage())
            .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
