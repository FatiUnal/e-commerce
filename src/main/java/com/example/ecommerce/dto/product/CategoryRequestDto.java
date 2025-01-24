package com.example.ecommerce.dto.product;


public class CategoryRequestDto {
    private final String name;
    private final String description;
    private final int parentCategoryId;
    private final String href;

    public CategoryRequestDto(String name, String description, int parentCategoryId, String href) {
        this.name = name;
        this.description = description;
        this.parentCategoryId = parentCategoryId;
        this.href = href;
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

    public String getHref() {
        return href;
    }
}
