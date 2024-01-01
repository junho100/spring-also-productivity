package com.junho.productmgnt.common.filter;

import com.junho.productmgnt.common.exception.BaseException;
import com.junho.productmgnt.common.response.BaseResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch(Exception exception) {
            setErrorResponse(request, response, exception);
        }
    }

    public void setErrorResponse(HttpServletRequest request, HttpServletResponse response, Exception exception)
        throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=UTF-8");
        BaseResponse<Object> errorResponse = BaseResponse.builder()
            .isSuccess(false)
            .message(exception.getMessage())
            .build();
        response.getWriter().write(errorResponse.convertToJson());
    }
}
