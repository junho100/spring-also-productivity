package com.junho.productmgnt.domains.product_audit;

import com.junho.productmgnt.common.response.BaseResponse;
import com.junho.productmgnt.common.response.BaseResponseService;
import com.junho.productmgnt.domains.product_audit.entity.ProductAudit;
import com.junho.productmgnt.domains.product_audit.model.dto.GetProductAuditsWithPageDto;
import com.junho.productmgnt.domains.product_audit.model.response.GetProductAuditsResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-audits")
public class ProductAuditController {
    private final ProductAuditService productAuditService;
    private final BaseResponseService baseResponseService;


    @GetMapping
    public ResponseEntity<BaseResponse<GetProductAuditsResponse>> getProjectAudits(Pageable pageable) {
        GetProductAuditsWithPageDto getProductAuditsWithPageDto = productAuditService.getProductAuditsWithPage(pageable);

        GetProductAuditsResponse getProductAuditsResponse = GetProductAuditsResponse.of(getProductAuditsWithPageDto);
        BaseResponse<GetProductAuditsResponse> response = baseResponseService.createSuccessResponse(getProductAuditsResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
