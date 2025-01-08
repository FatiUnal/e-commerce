package com.example.ecommerce.entity.product.image;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "ProductImage")
@DiscriminatorValue("PRODUCT_IMAGE")
public class ProductBaseImage extends BaseImage {
    private int order;

    public ProductBaseImage(String url, int order) {
        super(url, ImageType.PRODUCT_IMAGE);
        this.order = order;
    }

    public ProductBaseImage() {}

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
