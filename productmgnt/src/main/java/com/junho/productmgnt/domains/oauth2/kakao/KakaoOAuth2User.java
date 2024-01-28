package com.junho.productmgnt.domains.oauth2.kakao;

import com.junho.productmgnt.domains.oauth2.OAuth2UserInfo;
import java.util.Map;

public class KakaoOAuth2User extends OAuth2UserInfo {

    private String id;

    public KakaoOAuth2User(Map<String, Object> attributes) {
        super((Map<String, Object>) attributes.get("kakao_account"));
        this.id = String.valueOf(attributes.get("id"));
    }

    @Override
    public String getOAuth2Id() {
        return this.id;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}