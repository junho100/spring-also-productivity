package com.junho.productmgnt.domains.product;

import com.junho.productmgnt.common.response.BaseResponse;
import com.junho.productmgnt.common.response.BaseResponseService;
import com.junho.productmgnt.domains.product.entity.Product;
import com.junho.productmgnt.domains.product.model.command.CreateProductCommand;
import com.junho.productmgnt.domains.product.model.command.UpdateProductCommand;
import com.junho.productmgnt.domains.product.model.request.CreateProductRequest;
import com.junho.productmgnt.domains.product.model.request.UpdateProductRequest;
import com.junho.productmgnt.domains.product.model.response.CreateProductResponse;
import com.junho.productmgnt.domains.product.model.response.GetProductDetailResponse;
import com.junho.productmgnt.domains.product.model.response.GetProductDetailResponse.GetProductDetailAudit;
import com.junho.productmgnt.domains.product.model.response.UpdateProductResponse;
import com.junho.productmgnt.domains.product_audit.ProductAuditService;
import com.junho.productmgnt.domains.product_audit.entity.ProductAudit;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final BaseResponseService baseResponseService;
    private final ProductAuditService productAuditService;

    @PostMapping
    public ResponseEntity<BaseResponse<CreateProductResponse>> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        CreateProductCommand createProductCommand = CreateProductCommand.of(createProductRequest);

        Product product = productService.createProduct(createProductCommand);

        CreateProductResponse createProductResponse = CreateProductResponse.builder()
            .productId(product.getProductId())
            .build();
        BaseResponse<CreateProductResponse> response = baseResponseService.createSuccessResponse(createProductResponse);

        return new ResponseEntity<BaseResponse<CreateProductResponse>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<BaseResponse<UpdateProductResponse>> updateProduct(@RequestBody UpdateProductRequest updateProductRequest, @PathVariable Long productId) {
        UpdateProductCommand updateProductCommand = UpdateProductCommand.of(updateProductRequest, productId);

        Long updatedProductId = productService.updateProductById(updateProductCommand);

        UpdateProductResponse updateProductResponse = UpdateProductResponse.builder()
            .productId(updatedProductId)
            .build();
        BaseResponse<UpdateProductResponse> response = baseResponseService.createSuccessResponse(updateProductResponse);

        return new ResponseEntity<BaseResponse<UpdateProductResponse>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponse<GetProductDetailResponse>> getProductDetail(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        List<ProductAudit> productAudits = productAuditService.getProductAuditsByProductId(productId);

        List<GetProductDetailAudit> getProductDetailAudits = productAudits.stream()
            .map(productAudit -> GetProductDetailAudit.of(productAudit))
            .collect(Collectors.toList());
        GetProductDetailResponse getProductDetailResponse = GetProductDetailResponse.builder()
            .productId(product.getProductId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .category(product.getCategory().getCategoryName())
            .audits(getProductDetailAudits)
            .build();
        BaseResponse<GetProductDetailResponse> response = baseResponseService.createSuccessResponse(getProductDetailResponse);

        return new ResponseEntity<BaseResponse<GetProductDetailResponse>>(response, HttpStatus.OK);
    }
}
