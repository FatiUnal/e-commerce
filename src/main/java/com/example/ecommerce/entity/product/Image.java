package com.example.ecommerce.entity.product;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "img")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String filename;
    private int orders;
    private String url;
    private LocalDateTime createdDt;

    @Enumerated(EnumType.STRING)
    private ImageType type;

    public Image(String filename, String url, ImageType type) {
        this.filename = filename;
        this.url = url;
        this.type = type;
        createdDt = LocalDateTime.now();
    }

    public Image(String filename, String url, ImageType type,int orders) {
        this.filename = filename;
        this.orders = orders;
        this.url = url;
        this.type = type;
    }

    public Image() {
        createdDt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(LocalDateTime createdDt) {
        this.createdDt = createdDt;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }
}
