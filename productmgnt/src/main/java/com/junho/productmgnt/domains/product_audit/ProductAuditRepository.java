package com.junho.productmgnt.domains.product_audit;

import com.junho.productmgnt.domains.product_audit.entity.ProductAudit;
import com.junho.productmgnt.domains.product_audit.entity.ProductAuditId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAuditRepository extends JpaRepository<ProductAudit, ProductAuditId> {
    List<ProductAudit> findByProduct_ProductId(Long productId);
}
