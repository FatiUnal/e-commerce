package com.example.ecommerce.repository.product;


import com.example.ecommerce.entity.product.Category;
import com.example.ecommerce.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByCategories(Category category);
    List<Product> findAllByQuantityLessThan(int quantity);

}
