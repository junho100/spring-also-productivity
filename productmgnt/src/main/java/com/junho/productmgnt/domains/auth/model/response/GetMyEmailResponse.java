package com.junho.productmgnt.domains.auth.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetMyEmailResponse {
    private String email;
}
