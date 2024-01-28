package com.junho.productmgnt.common.security;

import static com.junho.productmgnt.common.security.CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import com.junho.productmgnt.common.response.BaseResponse;
import com.junho.productmgnt.common.util.CookieProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        BaseResponse<Object> errorResponse = BaseResponse.builder()
            .isSuccess(false)
            .message(authException.getMessage())
            .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(errorResponse.convertToJson());
        cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}