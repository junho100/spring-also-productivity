package com.junho.productmgnt.domains.auth.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignInDto {
    private String token;
    private Long userId;
}
