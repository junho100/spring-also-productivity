package com.junho.productmgnt.domains.auth;

import com.junho.productmgnt.common.exception.BaseException;
import com.junho.productmgnt.common.exception.BaseExceptionStatus;
import com.junho.productmgnt.common.util.JwtProvider;
import com.junho.productmgnt.domains.admin.AdminService;
import com.junho.productmgnt.domains.admin.entity.Admin;
import com.junho.productmgnt.domains.admin.model.command.CreateAdminCommand;
import com.junho.productmgnt.domains.auth.model.command.AdminSignInCommand;
import com.junho.productmgnt.domains.auth.model.command.AdminSignUpCommand;
import com.junho.productmgnt.domains.auth.model.command.SignInCommand;
import com.junho.productmgnt.domains.auth.model.command.SignUpCommand;
import com.junho.productmgnt.domains.auth.model.dto.AdminSignInDto;
import com.junho.productmgnt.domains.auth.model.dto.SignInDto;
import com.junho.productmgnt.domains.user.UserService;
import com.junho.productmgnt.domains.user.entity.User;
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
    private final JwtProvider jwtProvider;
    private final AdminService adminService;

    public Long signUp(SignUpCommand signUpCommand) {
        CreateUserCommand createUserCommand = CreateUserCommand.of(signUpCommand, bCryptPasswordEncoder.encode(signUpCommand.getPassword()));
        Long userId = userService.createUser(createUserCommand);

        return userId;
    }

    public SignInDto signIn(SignInCommand signInCommand) {
        User user = userService.getUserByEmail(signInCommand.getEmail());

        if (!checkIsPasswordValid(signInCommand.getPassword(), user.getPasswordHash())) {
            throw new BaseException(BaseExceptionStatus.SIGNIN_FAILED);
        }

        String token = jwtProvider.createToken(signInCommand.getEmail());

        SignInDto signInDto = SignInDto.builder()
            .token(token)
            .userId(user.getUserId())
            .build();
        return signInDto;
    }

    private boolean checkIsPasswordValid(String password, String passwordHashed) {
        if (!bCryptPasswordEncoder.matches(password, passwordHashed)) {
            return false;
        }

        return true;
    }

    public Long adminSignUp(AdminSignUpCommand adminSignUpCommand) {
        CreateAdminCommand createAdminCommand = CreateAdminCommand.of(adminSignUpCommand, bCryptPasswordEncoder.encode(adminSignUpCommand.getPassword()));
        Long adminId = adminService.createAdmin(createAdminCommand);

        return adminId;
    }

    public AdminSignInDto adminSignIn(AdminSignInCommand adminSignInCommand) {
        Admin admin = adminService.getAdminByEmail(adminSignInCommand.getEmail());

        if (!checkIsPasswordValid(adminSignInCommand.getPassword(), admin.getPasswordHash())) {
            throw new BaseException(BaseExceptionStatus.SIGNIN_FAILED);
        }

        String token = jwtProvider.createAdminToken(adminSignInCommand.getEmail());

        AdminSignInDto adminSignInDto = AdminSignInDto.builder()
            .token(token)
            .adminId(admin.getAdminId())
            .build();
        return adminSignInDto;
    }
}
