package com.junho.productmgnt.domains.user.model.command;

import com.junho.productmgnt.domains.auth.model.command.SignUpCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateUserCommand {
    private String username;
    private String email;
    private String passwordHash;

    public static CreateUserCommand of(SignUpCommand signUpCommand, String passwordHash) {
        return CreateUserCommand.builder()
            .username(signUpCommand.getUsername())
            .email(signUpCommand.getEmail())
            .passwordHash(passwordHash)
            .build();
    }
}
