package com.junho.productmgnt.domains.product;

import com.junho.productmgnt.domains.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
