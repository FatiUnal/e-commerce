package com.example.ecommerce.entity.product.image;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "CoverImage")
@DiscriminatorValue("COVER_IMAGE")
public class CoverImage extends BaseImage {

    public CoverImage(String url, ImageType imageType) {
        super(url, imageType);
    }

    public CoverImage() {

    }
}