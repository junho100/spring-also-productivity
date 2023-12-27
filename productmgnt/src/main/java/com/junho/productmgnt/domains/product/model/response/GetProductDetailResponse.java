package com.junho.productmgnt.domains.product.model.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetProductDetailResponse {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private String category;
    private List<GetProductDetailAudit> audits;
}

