package com.example.ecommerce.dto.product;

import java.util.List;

public class ProductRequestDto {
    private String productName;
    private String description;
    private float price;
    private List<Integer> categoryId;

    public ProductRequestDto(String productName, String description, float price, List<Integer> categoryId) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Integer> getCategoryId() {
        return categoryId;
    }
}
