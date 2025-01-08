package com.example.ecommerce.service.product;

import com.example.ecommerce.builder.product.CategoryBuilder;
import com.example.ecommerce.dto.product.CategoryRequestDto;
import com.example.ecommerce.entity.product.Category;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.product.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryBuilder categoryBuilder;


    public CategoryService(CategoryRepository categoryRepository, CategoryBuilder categoryBuilder) {
        this.categoryRepository = categoryRepository;
        this.categoryBuilder = categoryBuilder;
    }


    public Category save(CategoryRequestDto categoryRequestDto) {
        if (categoryRequestDto.getName() == null || categoryRequestDto.getName().isBlank()) {
            throw new IllegalArgumentException("Category name is required.");
        }

        categoryRepository
                .existsByNameEqualsIgnoreCase(categoryRequestDto.getName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category name."));


        Category category = new Category();
        if (categoryRequestDto.getParentCategoryId() != 0 ) {
            Category parentCategory = findById(categoryRequestDto.getParentCategoryId());
            category.setParentCategory(parentCategory);
        }

        category.setName(categoryRequestDto.getName());
        category.setDescription(categoryRequestDto.getDescription());
        return categoryRepository.save(category);
    }

    public List<Category> findPArentCategories() {
        return categoryRepository.findAllByParentCategoryIsNull();
    }






    public Category findById(int id) {
        return categoryRepository.
                findById(id).orElseThrow(() -> new NotFoundException("Category Not Found"));
    }


    public Category getCategoryById(Integer categoryId) {
        return findById(categoryId);
    }
}
