package com.junho.productmgnt.domains.product.model.command;

import com.junho.productmgnt.domains.product.model.request.UpdateProductRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateProductCommand {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private String category;

    public static UpdateProductCommand of(UpdateProductRequest updateProductRequest, Long productId) {
        return UpdateProductCommand.builder()
            .productId(productId)
            .name(updateProductRequest.getName())
            .description(updateProductRequest.getDescription())
            .price(updateProductRequest.getPrice())
            .category(updateProductRequest.getCategory())
            .build();
    }
}
