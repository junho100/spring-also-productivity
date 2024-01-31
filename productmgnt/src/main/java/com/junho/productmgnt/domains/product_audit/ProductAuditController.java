package com.junho.productmgnt.domains.product_audit;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-audits")
public class ProductAuditController {
    private final ProductAuditService productAuditService;


    @GetMapping
    public String test() {
        return "test";
    }
}
