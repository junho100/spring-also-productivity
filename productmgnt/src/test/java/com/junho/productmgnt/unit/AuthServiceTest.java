package com.junho.productmgnt.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.junho.productmgnt.common.util.JwtProvider;
import com.junho.productmgnt.domains.auth.AuthService;
import com.junho.productmgnt.domains.auth.model.command.SignInCommand;
import com.junho.productmgnt.domains.auth.model.dto.SignInDto;
import com.junho.productmgnt.domains.user.UserService;
import com.junho.productmgnt.domains.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void signInSuccess() {
        String TEST_EMAIL = "test@test.com";
        String TEST_USERNAME = "test-user";
        String TEST_PASSWORD = "asdasdasdasdas";
        String TEST_TOKEN = "token";
        User testUser = User.builder()
                .email(TEST_EMAIL)
                    .username(TEST_USERNAME)
                        .passwordHash(TEST_PASSWORD)
                            .build();

        when(userService.getUserByEmail(any())).thenReturn(testUser);
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);
        when(jwtProvider.createToken(any())).thenReturn(TEST_TOKEN);

        SignInCommand signInCommand = SignInCommand.builder()
            .email(TEST_EMAIL)
            .password(TEST_PASSWORD)
            .build();
        SignInDto signInDto = authService.signIn(signInCommand);

        Assertions.assertEquals(signInDto.getToken(), TEST_TOKEN);
    }
}
