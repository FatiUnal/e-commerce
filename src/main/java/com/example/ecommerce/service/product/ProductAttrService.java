package com.example.ecommerce.service.product;

import com.example.ecommerce.builder.product.ProductAttrBuilder;
import com.example.ecommerce.dto.product.productAttr.MultiSelectAttrRequestDto;
import com.example.ecommerce.dto.product.productAttr.SingleSelectAttrRequestDto;
import com.example.ecommerce.dto.product.productAttr.TextAttrRequestDto;
import com.example.ecommerce.entity.product.Product;
import com.example.ecommerce.entity.product.productattr.MultiSelectAttr;
import com.example.ecommerce.entity.product.productattr.ProductAttribute;
import com.example.ecommerce.entity.product.productattr.SingleSelectAttr;
import com.example.ecommerce.entity.product.productattr.TextAttr;
import com.example.ecommerce.repository.product.productattr.MultiSelectAttrRepository;
import com.example.ecommerce.repository.product.productattr.SingleSelectAttrRepository;
import com.example.ecommerce.repository.product.productattr.TextAttrRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttrService {
    private final SingleSelectAttrRepository singleSelectAttrRepository;
    private final MultiSelectAttrRepository multiSelectAttrRepository;
    private final TextAttrRepository textAttrRepository;
    private final ProductAttrBuilder productAttrBuilder;

    public ProductAttrService(SingleSelectAttrRepository singleSelectAttrRepository, MultiSelectAttrRepository multiSelectAttrRepository, TextAttrRepository textAttrRepository, ProductAttrBuilder productAttrBuilder) {
        this.singleSelectAttrRepository = singleSelectAttrRepository;
        this.multiSelectAttrRepository = multiSelectAttrRepository;
        this.textAttrRepository = textAttrRepository;
        this.productAttrBuilder = productAttrBuilder;
    }


    public SingleSelectAttr createSingleSelectAttr(SingleSelectAttrRequestDto singleSelectAttrRequestDto) {
        SingleSelectAttr singleSelectAttr = productAttrBuilder.singleSelectAttrRequestDtoToSingleSelectAttr(singleSelectAttrRequestDto);
        return singleSelectAttrRepository.save(singleSelectAttr);
    }

    public MultiSelectAttr createMultiSelectAttr(MultiSelectAttrRequestDto multiSelectAttrRequestDto){
        MultiSelectAttr multiSelectAttr = productAttrBuilder.multiSelectAttrRequestDtoToMultiSelectAttr(multiSelectAttrRequestDto);
        return multiSelectAttrRepository.save(multiSelectAttr);
    }

    public TextAttr createTextAttr(TextAttrRequestDto textAttrRequestDto) {
        TextAttr textAttr = productAttrBuilder.textAttrRequestDtoToTextAttr(textAttrRequestDto);
        return textAttrRepository.save(textAttr);
    }

    public ProductAttribute getProductAttr(Product product) {
        List<ProductAttribute> productAttributes = product.getProductAttributes();
        return null;
    }





}
