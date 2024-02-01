package com.junho.productmgnt.domains.product_audit.entity;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class ProductAuditId implements Serializable {
    private Long product;
    private Long user;
    private Long auditId;
}
