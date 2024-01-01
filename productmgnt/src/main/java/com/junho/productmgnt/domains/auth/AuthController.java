package com.junho.productmgnt.domains.auth;

import com.junho.productmgnt.common.response.BaseResponse;
import com.junho.productmgnt.common.response.BaseResponseService;
import com.junho.productmgnt.domains.auth.model.command.SignInCommand;
import com.junho.productmgnt.domains.auth.model.command.SignUpCommand;
import com.junho.productmgnt.domains.auth.model.dto.SignInDto;
import com.junho.productmgnt.domains.auth.model.request.SignInRequest;
import com.junho.productmgnt.domains.auth.model.request.SignUpRequest;
import com.junho.productmgnt.domains.auth.model.response.SignInResponse;
import com.junho.productmgnt.domains.auth.model.response.SignUpResponse;
import jakarta.validation.Valid;
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
    public ResponseEntity<BaseResponse<SignUpResponse>> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        Long userId = authService.signUp(SignUpCommand.of(signUpRequest));
        SignUpResponse signUpResponse = SignUpResponse.builder()
            .userId(userId)
            .build();
        BaseResponse<SignUpResponse> response = baseResponseService.createSuccessResponse(signUpResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<BaseResponse<SignInResponse>> signIn(@RequestBody SignInRequest signInRequest) {
        SignInCommand signInCommand = SignInCommand.of(signInRequest);
        SignInDto signInDto = authService.signIn(signInCommand);

        SignInResponse signInResponse = SignInResponse.builder()
            .token(signInDto.getToken())
            .userId(signInDto.getUserId())
            .build();
        BaseResponse<SignInResponse> response = baseResponseService.createSuccessResponse(signInResponse);
        return new ResponseEntity<BaseResponse<SignInResponse>>(response, HttpStatus.CREATED);
    }
}
