package com.junho.productmgnt.domains.product.model.response;

import com.junho.productmgnt.domains.product_audit.entity.ProductAudit;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetProductDetailAudit {
    private Long auditId;
    private Long userId;
    private LocalDateTime timestamp;
    private String action;

    public static GetProductDetailAudit of(ProductAudit productAudit) {
        return GetProductDetailAudit.builder()
            .auditId(productAudit.getAuditId())
            .userId(productAudit.getUser().getUserId())
            .timestamp(productAudit.getTimestamp())
            .action(productAudit.getAction())
            .build();
    }
}
