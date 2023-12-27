package com.junho.productmgnt.domains.auth;

import com.junho.productmgnt.common.response.BaseResponse;
import com.junho.productmgnt.common.response.BaseResponseService;
import com.junho.productmgnt.domains.auth.model.command.SignUpCommand;
import com.junho.productmgnt.domains.auth.model.request.SignUpRequest;
import com.junho.productmgnt.domains.auth.model.response.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final BaseResponseService baseResponseService;

    @PostMapping("/sign-up")
    public ResponseEntity<BaseResponse<SignUpResponse>> signUp(@RequestBody SignUpRequest signUpRequest) {
        Long userId = authService.signUp(SignUpCommand.of(signUpRequest));
        SignUpResponse signUpResponse = SignUpResponse.builder()
            .userId(userId)
            .build();
        BaseResponse<SignUpResponse> response = baseResponseService.createSuccessResponse(signUpResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
