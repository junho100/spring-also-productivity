package com.junho.productmgnt.domains.auth.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignInRequest {
    private String email;
    private String password;
}
