package com.junho.productmgnt.domains.product;

import com.junho.productmgnt.common.exception.BaseException;
import com.junho.productmgnt.common.exception.BaseExceptionStatus;
import com.junho.productmgnt.domains.category.CategoryService;
import com.junho.productmgnt.domains.category.entity.Category;
import com.junho.productmgnt.domains.product.entity.Product;
import com.junho.productmgnt.domains.product.model.command.CreateProductCommand;
import com.junho.productmgnt.domains.product.model.command.UpdateProductCommand;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Product createProduct(CreateProductCommand createProductCommand) {
        String categoryName = createProductCommand.getCategory();
        if (categoryService.checkCategoryExistsByName(categoryName)) {
            Category category = categoryService.getCategoryByName(categoryName);

            Product product = Product.builder()
                .name(createProductCommand.getName())
                .description(createProductCommand.getDescription())
                .price(createProductCommand.getPrice())
                .category(category)
                .build();

            Product savedProduct = productRepository.save(product);

            return savedProduct;
        }

        Category category = categoryService.createCategory(categoryName);

        Product product = Product.builder()
            .name(createProductCommand.getName())
            .description(createProductCommand.getDescription())
            .price(createProductCommand.getPrice())
            .category(category)
            .build();

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent()) {
            throw new BaseException(BaseExceptionStatus.PRODUCT_NOT_FOUND);
        }

        return product.get();
    }

    public Long updateProductById(UpdateProductCommand updateProductCommand) {
        String categoryName = updateProductCommand.getCategory();
        Product product = getProductById(updateProductCommand.getProductId());

        if (categoryService.checkCategoryExistsByName(categoryName)) {
            Category category = categoryService.getCategoryByName(categoryName);

            product.update(
                updateProductCommand.getName(),
                updateProductCommand.getDescription(),
                updateProductCommand.getPrice(),
                category);

            return product.getProductId();
        }

        Category category = categoryService.createCategory(updateProductCommand.getCategory());

        product.update(
            updateProductCommand.getName(),
            updateProductCommand.getDescription(),
            updateProductCommand.getPrice(),
            category);

        return product.getProductId();
    }
}
