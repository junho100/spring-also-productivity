package com.junho.productmgnt.domains.oauth2;

import static com.junho.productmgnt.common.exception.BaseExceptionStatus.OAUTH2_EMAIL_NOT_FOUND;
import static com.junho.productmgnt.common.exception.BaseExceptionStatus.OAUTH2_USER_EXISTS;

import com.junho.productmgnt.common.exception.BaseException;
import com.junho.productmgnt.domains.auth.CustomUserDetails;
import com.junho.productmgnt.domains.user.UserRepository;
import com.junho.productmgnt.domains.user.entity.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>,
    UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

        return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }

    protected OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        //OAuth2 로그인 플랫폼 구분
        AuthProvider authProvider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(authProvider, oAuth2User.getAttributes());

        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new BaseException(OAUTH2_EMAIL_NOT_FOUND);
        }

        Optional<User> user = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        //이미 가입된 경우
        if (user.isPresent()) {
            if (!user.get().getAuthProvider().equals(authProvider)) {
                throw new BaseException(OAUTH2_USER_EXISTS);
            }
            return CustomUserDetails.create(updateUser(user.get(), oAuth2UserInfo), oAuth2UserInfo.getAttributes());
        }
        //가입되지 않은 경우
        return CustomUserDetails.create(registerUser(authProvider, oAuth2UserInfo), oAuth2UserInfo.getAttributes());
    }

    private User registerUser(AuthProvider authProvider, OAuth2UserInfo oAuth2UserInfo) {
        User user = User.builder()
            .email(oAuth2UserInfo.getEmail())
            .username(oAuth2UserInfo.getName())
            .oauth2Id(oAuth2UserInfo.getOAuth2Id())
            .authProvider(authProvider)
            .build();

        return userRepository.save(user);
    }

    private User updateUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        return userRepository.save(user.update(oAuth2UserInfo));
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new BaseException(OAUTH2_EMAIL_NOT_FOUND);
        }

        return CustomUserDetails.create(user.get());
    }
}
