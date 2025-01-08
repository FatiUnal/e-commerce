package com.example.ecommerce.entity.product.image;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "ProductCoverImage")
@DiscriminatorValue("PRODUCT_COVER_IMAGE")
public class ProductCoverBaseImage extends BaseImage {

    public ProductCoverBaseImage(String url) {
        super(url, ImageType.PRODUCT_COVER_IMAGE);
    }

    public ProductCoverBaseImage() {

    }
}