package com.example.ecommerce.controller;

import com.example.ecommerce.dto.product.CategoryRequestDto;
import com.example.ecommerce.dto.product.CategorySmallDto;
import com.example.ecommerce.entity.product.Category;
import com.example.ecommerce.service.product.CategoryService;
import jakarta.servlet.ServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<List<CategorySmallDto>> getParentCategory() {
        return new ResponseEntity<>(categoryService.findPArentCategories() , HttpStatus.OK);
    }
    @GetMapping("/by-id")
    public ResponseEntity<Category> getCategoryById(@RequestParam Integer categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId),HttpStatus.OK);
    }

    @PutMapping("/cover-image")
    public ResponseEntity<Category> updateCoverImage(@RequestParam("image") MultipartFile file, @RequestParam("id") int id) {
        return new ResponseEntity<>(categoryService.updateCoverImage(file,id),HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCategory(@RequestParam Integer categoryId) {
        return new ResponseEntity<>(categoryService.delete(categoryId),HttpStatus.OK);
    }

}
