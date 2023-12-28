package com.junho.productmgnt.domains.auth.model.command;

import com.junho.productmgnt.domains.auth.model.request.SignInRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignInCommand {
    private String email;
    private String password;

    public static SignInCommand of(SignInRequest signInRequest) {
        return SignInCommand.builder()
            .email(signInRequest.getEmail())
            .password(signInRequest.getPassword())
            .build();
    }
}
