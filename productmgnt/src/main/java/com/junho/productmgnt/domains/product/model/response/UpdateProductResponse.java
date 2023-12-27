package com.junho.productmgnt.domains.product.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateProductResponse {
    private Long productId;
}
