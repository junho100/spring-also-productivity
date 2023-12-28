package com.junho.productmgnt.domains.category;

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
    public Boolean checkCategoryExistsByName(String categoryName) { //TODO: boolean 원시타입으로 변경
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
            return null; //TODO: add exceptions
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
