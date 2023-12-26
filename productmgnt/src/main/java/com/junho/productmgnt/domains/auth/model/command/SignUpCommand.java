package com.junho.productmgnt.domains.auth.model.command;

import com.junho.productmgnt.domains.auth.model.request.SignUpRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpCommand {
    private String username;
    private String email;
    private String password;

    public static SignUpCommand of(SignUpRequest signUpRequest) {
        return SignUpCommand.builder()
            .username(signUpRequest.getUsername())
            .email(signUpRequest.getEmail())
            .password(signUpRequest.getPassword())
            .build();
    }
}
