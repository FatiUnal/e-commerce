package com.example.ecommerce.repository.product.Image;

import com.example.ecommerce.entity.product.image.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findById(int id);
}
