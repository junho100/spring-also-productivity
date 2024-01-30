package com.junho.productmgnt.domains.auth.model.command;

import com.junho.productmgnt.domains.auth.model.request.AdminSignUpRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdminSignUpCommand {
    private String email;
    private String password;

    public static AdminSignUpCommand of(AdminSignUpRequest adminSignUpRequest) {
        return AdminSignUpCommand.builder()
                .email(adminSignUpRequest.getEmail())
                .password(adminSignUpRequest.getPassword())
                .build();
    }
}
