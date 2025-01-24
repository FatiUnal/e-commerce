package com.example.ecommerce.entity.product;

import com.example.ecommerce.entity.product.image.CoverImage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cover_image_id")
    private CoverImage coverImage;
    private String href;

    @ManyToOne
    @JoinColumn(name = "parent_id") // Veritabanındaki sütun adı
    @JsonIgnore
    private Category parentCategory;

    // Alt kategoriler
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> subCategories = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "category_product", // Ortak tablo adı
            joinColumns = @JoinColumn(name = "category_id"), // Category'nin sütunu
            inverseJoinColumns = @JoinColumn(name = "product_id") // Product'un sütunu
    )
    private Set<Product> products = new HashSet<>();

    public Category(int id, String name, String description, String href, Category parentCategory, Set<Category> subCategories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.href = href;
        this.parentCategory = parentCategory;
        this.subCategories = subCategories;
    }

    public Category(String name, String description, String href, Category parentCategory, Set<Category> subCategories) {
        this.name = name;
        this.description = description;
        this.href = href;
        this.parentCategory = parentCategory;
        this.subCategories = subCategories;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<Category> subCategories) {
        this.subCategories = subCategories;
    }


    public CoverImage getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
