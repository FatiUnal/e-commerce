package com.example.ecommerce.entity.product;

import com.example.ecommerce.entity.product.image.BaseImage;
import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private int id;
    private String productName;
    private String description;
    private float price;
    private int quantity;
    private Category category;
    private BaseImage coverBaseImage;




}
