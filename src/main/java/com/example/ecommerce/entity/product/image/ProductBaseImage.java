package com.example.ecommerce.entity.product.image;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "ProductImage")
@DiscriminatorValue("PRODUCT_IMAGE")
public class ProductBaseImage extends BaseImage {
    private int orders;

    public ProductBaseImage(String url, int orders) {
        super(url, ImageType.PRODUCT_IMAGE);
        this.orders = orders;
    }

    public ProductBaseImage() {}

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }
}
