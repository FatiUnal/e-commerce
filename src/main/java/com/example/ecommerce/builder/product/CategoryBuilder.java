package com.example.ecommerce.builder.product;

import com.example.ecommerce.dto.product.CategorySmallDto;
import com.example.ecommerce.entity.product.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryBuilder {

    public CategorySmallDto buildCategory(Category category) {

        List<CategorySmallDto> subCategoryDtos = category.getSubCategories().stream()
                .map(this::buildCategory)
                .collect(Collectors.toList());
        String url="";
        if (category.getCoverImage()!=null)
            url = category.getCoverImage().getUrl();

        return new CategorySmallDto(
                category.getId(),
                category.getName(),
                url,
                subCategoryDtos
        );
    }

}
