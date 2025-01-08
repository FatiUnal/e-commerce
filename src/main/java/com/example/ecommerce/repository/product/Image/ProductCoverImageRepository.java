package com.example.ecommerce.repository.product.Image;

import com.example.ecommerce.entity.product.image.ProductCoverBaseImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCoverImageRepository extends JpaRepository<ProductCoverBaseImage, Long> {
}
