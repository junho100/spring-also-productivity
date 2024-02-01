package com.junho.productmgnt.domains.product_audit.model.response;

import com.junho.productmgnt.domains.product_audit.entity.ProductAudit;
import com.junho.productmgnt.domains.product_audit.model.dto.GetProductAuditsWithPageDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetProductAuditsResponse {
    public static GetProductAuditsResponse of(
        GetProductAuditsWithPageDto getProductAuditsWithPageDto) {
        List<ProductAudits> productAudits = getProductAuditsWithPageDto.getProductAudits()
            .stream()
            .map(ProductAudits::from)
            .collect(Collectors.toList());
        return GetProductAuditsResponse.builder()
            .productAudits(productAudits)
            .totalNumberOfElements(getProductAuditsWithPageDto.getTotalCount())
            .totalNumberOfPages(getProductAuditsWithPageDto.getTotalPage())
            .build();
    }

    @Getter
    @Builder
    public static class ProductAudits {
        private Long auditId;
        private Long productId;
        private Long userId;
        private LocalDateTime timestamp;

        public static ProductAudits from(ProductAudit productAudit) {
            return ProductAudits.builder()
                .auditId(productAudit.getAuditId())
                .productId(productAudit.getProduct().getProductId())
                .userId(productAudit.getUser().getUserId())
                .timestamp(productAudit.getTimestamp())
                .build();
        }
    }

    private List<ProductAudits> productAudits;
    private Long totalNumberOfElements;
    private Integer totalNumberOfPages;
}
