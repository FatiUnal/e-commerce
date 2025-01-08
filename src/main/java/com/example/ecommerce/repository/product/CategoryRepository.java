package com.example.ecommerce.repository.product;

import com.example.ecommerce.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Eğer name alanı için büyük/küçük harf duyarsız bir indeks oluşturursanız, sorgu performansı artar.
     * PostgreSQL kullanıyorsanız, name sütununa CREATE UNIQUE INDEX idx_category_name_ci ON category (LOWER(name)); komutuyla bir indeks oluşturabilirsiniz.
     */
    Optional<Category> existsByNameEqualsIgnoreCase(String name);
    List<Category> findAllByParentCategoryIsNull();


}
