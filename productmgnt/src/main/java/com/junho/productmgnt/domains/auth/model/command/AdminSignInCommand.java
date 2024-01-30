package com.junho.productmgnt.domains.auth.model.command;

import com.junho.productmgnt.domains.auth.model.request.AdminSignInRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdminSignInCommand {
    private String email;
    private String password;

    public static AdminSignInCommand of(AdminSignInRequest adminSignInRequest) {
        return AdminSignInCommand.builder()
            .email(adminSignInRequest.getEmail())
            .password(adminSignInRequest.getPassword())
            .build();
    }
}
