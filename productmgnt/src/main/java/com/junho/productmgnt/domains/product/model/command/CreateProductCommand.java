package com.junho.productmgnt.domains.product.model.command;

import com.junho.productmgnt.domains.product.model.request.CreateProductRequest;
import com.junho.productmgnt.domains.user.model.command.CreateUserCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateProductCommand {
    private String name;
    private String description;
    private Double price;
    private String category;

    public static CreateProductCommand of(CreateProductRequest createProductRequest) {
        return CreateProductCommand.builder()
            .name(createProductRequest.getName())
            .description(createProductRequest.getDescription())
            .price(createProductRequest.getPrice())
            .category(createProductRequest.getCategory())
            .build();
    }
}
