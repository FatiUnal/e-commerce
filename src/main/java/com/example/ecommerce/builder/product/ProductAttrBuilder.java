package com.example.ecommerce.builder.product;

import com.example.ecommerce.dto.product.productAttr.MultiSelectAttrRequestDto;
import com.example.ecommerce.dto.product.productAttr.SingleSelectAttrRequestDto;
import com.example.ecommerce.dto.product.productAttr.TextAttrRequestDto;
import com.example.ecommerce.entity.product.productattr.MultiSelectAttr;
import com.example.ecommerce.entity.product.productattr.SingleSelectAttr;
import com.example.ecommerce.entity.product.productattr.TextAttr;
import org.springframework.stereotype.Component;

@Component
public class ProductAttrBuilder {
    public SingleSelectAttr singleSelectAttrRequestDtoToSingleSelectAttr(SingleSelectAttrRequestDto singleSelectAttrRequestDto) {
        return new SingleSelectAttr(singleSelectAttrRequestDto.getName(),singleSelectAttrRequestDto.getSelect());
    }

    public MultiSelectAttr multiSelectAttrRequestDtoToMultiSelectAttr(MultiSelectAttrRequestDto multiSelectAttrRequestDto) {
        return new MultiSelectAttr(multiSelectAttrRequestDto.getName(),multiSelectAttrRequestDto.getSelect());
    }

    public TextAttr textAttrRequestDtoToTextAttr(TextAttrRequestDto textAttrRequestDto){
        return new TextAttr(textAttrRequestDto.getName());
    }
}
