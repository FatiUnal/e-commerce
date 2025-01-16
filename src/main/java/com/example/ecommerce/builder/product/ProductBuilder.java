package com.example.ecommerce.builder.product;

import com.example.ecommerce.dto.product.ProductRequestDto;
import com.example.ecommerce.dto.product.ProductResponseDto;
import com.example.ecommerce.dto.product.ProductSmallResponseDto;
import com.example.ecommerce.entity.product.Product;
import com.example.ecommerce.entity.product.image.BaseImage;
import org.springframework.stereotype.Component;

@Component
public class ProductBuilder {

    public Product productRequestDtoToProduct(ProductRequestDto productRequestDto) {
        return new Product(
                productRequestDto.getProductName(),
                productRequestDto.getDescription(),
                productRequestDto.getQuantity(),
                productRequestDto.getPrice(),
                productRequestDto.isStatus()
        );
    }

    public ProductResponseDto productToProductResponseDto(Product product){
        return new ProductResponseDto(product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getProductImages().stream().map(BaseImage::getUrl).toList());
    }

    public ProductSmallResponseDto productToProductSmallResponseDto(Product product){
        String url = "";
        if (product.getCoverImage() != null) {
            url = product.getCoverImage().getUrl();
        }

        return new ProductSmallResponseDto(
                product.getId(),
                product.getProductName(),
                url,
                product.getPrice()
        );
    }

}
