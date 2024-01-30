package com.junho.productmgnt.domains.admin.model.command;

import com.junho.productmgnt.domains.auth.model.command.AdminSignUpCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateAdminCommand {
    private String email;
    private String password;

    public static CreateAdminCommand of(AdminSignUpCommand adminSignUpCommand, String password){
        return CreateAdminCommand.builder()
                .email(adminSignUpCommand.getEmail())
                .password(password)
                .build();
    }
}
