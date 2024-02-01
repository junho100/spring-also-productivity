package com.junho.productmgnt.domains.product_audit.model.dto;

import com.junho.productmgnt.domains.product_audit.entity.ProductAudit;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetProductAuditsWithPageDto {
    private List<ProductAudit> productAudits;
    private Long totalCount;
    private Integer totalPage;
}
