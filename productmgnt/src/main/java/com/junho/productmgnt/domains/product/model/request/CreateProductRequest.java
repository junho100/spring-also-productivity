package com.junho.productmgnt.domains.product.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateProductRequest {
    private String name;
    private String description;
    private Double price;
    private String category;
}
