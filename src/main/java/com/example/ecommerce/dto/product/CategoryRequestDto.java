package com.example.ecommerce.dto.product;


public class CategoryRequestDto {
    private final String name;
    private final String description;
    private final int parentCategoryId;

    public CategoryRequestDto(String name, String description, int parentCategoryId) {
        this.name = name;
        this.description = description;
        this.parentCategoryId = parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

}
