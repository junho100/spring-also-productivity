package com.junho.productmgnt.domains.oauth2;

import static com.junho.productmgnt.common.exception.BaseExceptionStatus.INVALID_OAUTH_PROVIDER;

import com.junho.productmgnt.common.exception.BaseException;
import com.junho.productmgnt.domains.oauth2.kakao.KakaoOAuth2User;
import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
        switch (authProvider) {
            case KAKAO: return new KakaoOAuth2User(attributes);

            default: throw new BaseException(INVALID_OAUTH_PROVIDER);
        }
    }
}