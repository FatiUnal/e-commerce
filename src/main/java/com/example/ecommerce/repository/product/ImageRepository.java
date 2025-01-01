package com.example.ecommerce.repository.product;

import com.example.ecommerce.entity.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
