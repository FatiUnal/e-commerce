package com.example.ecommerce.repository.product.Image;

import com.example.ecommerce.entity.product.image.ProductBaseImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductBaseImage, Long> {
}
