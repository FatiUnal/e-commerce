package com.example.ecommerce.repository.product;


import com.example.ecommerce.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
