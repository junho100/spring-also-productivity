package com.junho.productmgnt.domains.user.entity;

import com.junho.productmgnt.domains.oauth2.AuthProvider;
import com.junho.productmgnt.domains.oauth2.OAuth2UserInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String username;

    @Column(unique = true)
    private String email;

    @Column
    private String passwordHash;

    @Builder
    public User (String username, String email, String passwordHash, AuthProvider authProvider, String oauth2Id){
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.authProvider = authProvider;
        this.oauth2Id = oauth2Id;
    }

    @Column
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Column
    private String oauth2Id;

    public User update(OAuth2UserInfo oAuth2UserInfo){
        this.oauth2Id = oAuth2UserInfo.getOAuth2Id();
        this.email = oAuth2UserInfo.getEmail();
        this.username = oAuth2UserInfo.getName();
        return this;
    }
}
