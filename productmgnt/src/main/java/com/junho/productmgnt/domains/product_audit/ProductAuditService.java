package com.junho.productmgnt.domains.product_audit;

import com.junho.productmgnt.common.exception.BaseException;
import com.junho.productmgnt.common.exception.BaseExceptionStatus;
import com.junho.productmgnt.domains.product_audit.entity.ProductAudit;
import com.junho.productmgnt.domains.product_audit.model.dto.GetProductAuditsWithPageDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductAuditService {
    private final ProductAuditRepository productAuditRepository;

    public List<ProductAudit> getProductAuditsByProductId(Long productId) {
        List<ProductAudit> productAudits = productAuditRepository.findByProduct_ProductId(productId);

        if (productAudits.isEmpty()) {
            throw new BaseException(BaseExceptionStatus.PRODUCT_AUDIT_NOT_FOUND);
        }

        return productAudits;
    }

    public GetProductAuditsWithPageDto getProductAuditsWithPage(Pageable pageable) {
        Page<ProductAudit> productAuditsPage = productAuditRepository.findAll(pageable);
        List<ProductAudit> productAudits = productAuditsPage.getContent();
        if (productAudits.isEmpty()) {
            throw new BaseException(BaseExceptionStatus.PRODUCT_AUDIT_NOT_FOUND);
        }

        return GetProductAuditsWithPageDto.builder()
            .productAudits(productAudits)
            .totalPage(productAuditsPage.getTotalPages())
            .totalCount(productAuditsPage.getTotalElements())
            .build();
    }
}
