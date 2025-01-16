package com.example.ecommerce.dto.product.productAttr;

import java.util.List;

public class SingleSelectAttrRequestDto {
    private String name;
    private List<String> select;

    public SingleSelectAttrRequestDto(String name, List<String> select) {
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
