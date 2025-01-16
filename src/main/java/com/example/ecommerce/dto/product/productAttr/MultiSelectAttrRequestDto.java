package com.example.ecommerce.dto.product.productAttr;

import java.util.List;

public class MultiSelectAttrRequestDto {
    private String name;
    private List<String> select;

    public MultiSelectAttrRequestDto(String name, List<String> select) {
        this.name = name;
        this.select = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSelect() {
        return select;
    }

    public void setSelect(List<String> select) {
        this.select = select;
    }
}
