package com.junho.productmgnt.domains.auth;

import com.junho.productmgnt.domains.auth.model.command.SignUpCommand;
import com.junho.productmgnt.domains.user.UserService;
import com.junho.productmgnt.domains.user.model.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    public Long signUp(SignUpCommand signUpCommand) {
        CreateUserCommand createUserCommand = CreateUserCommand.of(signUpCommand, bCryptPasswordEncoder.encode(signUpCommand.getPassword()));
        Long userId = userService.createUser(createUserCommand);

        return userId;
    }
}
