package com.example.ecommerce.controller;

import com.example.ecommerce.builder.product.ProductBuilder;
import com.example.ecommerce.dto.product.ProductRequestDto;
import com.example.ecommerce.dto.product.ProductSmallResponseDto;
import com.example.ecommerce.entity.product.Product;
import com.example.ecommerce.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return new ResponseEntity<>(productService.createProduct(productRequestDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestParam int id,@RequestBody ProductRequestDto productRequestDto) {
        return new ResponseEntity<>(productService.updateProduct(id, productRequestDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/by-quantity")
    public ResponseEntity<List<Product>> getProductByQuantity(@RequestParam(defaultValue = "10") int quantity) {
        return new ResponseEntity<>(productService.getProductByQuantity(quantity),HttpStatus.OK);
    }

    @GetMapping("/small")
    public ResponseEntity<List<ProductSmallResponseDto>> getProductSmall() {
        return new ResponseEntity<>(productService.getProductSmall(),HttpStatus.OK);
    }

    @PutMapping("/cover-image")
    public ResponseEntity<Product> updateCoverImage(@RequestParam("image") MultipartFile file,@RequestParam("id") int id) {
        return new ResponseEntity<>(productService.updateCoverImage(file,id),HttpStatus.CREATED);
    }

    @PutMapping("/product-image")
    public ResponseEntity<Product> updateProductImage(@RequestParam("images") MultipartFile[] files, @RequestParam("id") int id) {
        return new ResponseEntity<>(productService.updateProductImage(files,id),HttpStatus.CREATED);
    }


    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestParam("id") int id) {
        return new ResponseEntity<>(productService.deleteProduct(id),HttpStatus.OK);
    }

    @DeleteMapping("/delete-image")
    public ResponseEntity<String> deleteProductImage(@RequestParam("prodId") int prodId, @RequestParam("images") List<Integer> imagesId) {
        return new ResponseEntity<>(productService.deleteProductImage(prodId,imagesId),HttpStatus.OK);
    }


}
