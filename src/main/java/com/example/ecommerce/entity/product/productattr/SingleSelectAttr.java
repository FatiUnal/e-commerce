package com.example.ecommerce.entity.product.productattr;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SingleSelectAttr extends ProductAttribute {
    @ElementCollection
    @CollectionTable(name = "single_select_options", joinColumns = @JoinColumn(name = "single_select_id"))
    @Column(name = "option_value")
    private List<String> select;

    public SingleSelectAttr(String name, List<String> select) {
        super(name);
        this.select = select;
    }
    public SingleSelectAttr() {}

    public List<String> getSelect() {
        return select;
    }

    public void setSelect(List<String> select) {
        this.select = select;
    }
}
