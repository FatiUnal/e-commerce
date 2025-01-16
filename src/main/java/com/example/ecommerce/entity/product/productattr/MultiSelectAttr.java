package com.example.ecommerce.entity.product.productattr;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class MultiSelectAttr extends ProductAttribute {

    @ElementCollection
    @CollectionTable(name = "multi_select_options", joinColumns = @JoinColumn(name = "multi_select_options"))
    @Column(name = "option_value")
    private List<String> selected;

    public MultiSelectAttr(String name, List<String> selected) {
        super(name);
        this.selected = selected;
    }

    public MultiSelectAttr() {

    }

    public List<String> getSelected() {
        return selected;
    }

    public void setSelected(List<String> selected) {
        this.selected = selected;
    }
}
