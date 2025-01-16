package com.example.ecommerce.entity.product.productattr;

import jakarta.persistence.Entity;

@Entity
public class TextAttr extends ProductAttribute {
    private String text;

    public TextAttr(String text) {
        this.text = text;
    }

    public TextAttr() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
