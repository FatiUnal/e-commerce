package com.example.ecommerce.controller;

import com.example.ecommerce.entity.product.Category;
import com.example.ecommerce.service.product.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/parent")
    public ResponseEntity<List<Category>> getParentCategory() {
        return new ResponseEntity<>(categoryService.findPArentCategories() , HttpStatus.OK);
    }

}
