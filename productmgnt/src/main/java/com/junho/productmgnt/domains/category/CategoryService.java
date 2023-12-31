package com.junho.productmgnt.domains.category;

import com.junho.productmgnt.common.exception.BaseException;
import com.junho.productmgnt.common.exception.BaseExceptionStatus;
import com.junho.productmgnt.domains.category.entity.Category;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public boolean checkCategoryExistsByName(String categoryName) {
        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);

        if (!category.isPresent()) {
            return false;
        }

        return true;
    }

    @Transactional(readOnly = true)
    public Category getCategoryByName(String categoryName) {
        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);

        if (!category.isPresent()) {
            throw new BaseException(BaseExceptionStatus.CATEGORY_NOT_FOUND);
        }

        return category.get();
    }

    public Category createCategory(String categoryName) {
        Category category = Category.builder()
            .categoryName(categoryName)
            .build();

        Category savedCategory = categoryRepository.save(category);

        return savedCategory;
    }
}
