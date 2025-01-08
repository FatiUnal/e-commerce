package com.example.ecommerce.controller;

import com.example.ecommerce.dto.product.CategoryRequestDto;
import com.example.ecommerce.entity.product.Category;
import com.example.ecommerce.service.product.CategoryService;
import jakarta.servlet.ServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        return new ResponseEntity<>(categoryService.save(categoryRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/parent")
    public ResponseEntity<List<Category>> getParentCategory() {
        return new ResponseEntity<>(categoryService.findPArentCategories() , HttpStatus.OK);
    }
    @GetMapping("/by-id")
    public ResponseEntity<Category> getCategoryById(@RequestParam Integer categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId),HttpStatus.OK);
    }

}
