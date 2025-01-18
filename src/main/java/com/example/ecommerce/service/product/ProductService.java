package com.example.ecommerce.service.product;

import com.example.ecommerce.builder.product.ProductBuilder;
import com.example.ecommerce.dto.product.ProductRequestDto;
import com.example.ecommerce.dto.product.ProductSmallResponseDto;
import com.example.ecommerce.entity.product.Category;
import com.example.ecommerce.entity.product.Product;
import com.example.ecommerce.entity.product.image.CoverImage;
import com.example.ecommerce.entity.product.image.ImageType;
import com.example.ecommerce.entity.product.image.ProductImage;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.product.Image.CoverImageRepository;
import com.example.ecommerce.repository.product.Image.ProductImageRepository;
import com.example.ecommerce.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final CategoryService categoryService;
    private final ProductBuilder productBuilder;
    private final CoverImageRepository coverImageRepository;
    private final ProductImageRepository productImageRepository;

    public ProductService(ProductRepository productRepository, ImageService imageService, CategoryService categoryService, ProductBuilder productBuilder, CoverImageRepository coverImageRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.imageService = imageService;
        this.categoryService = categoryService;
        this.productBuilder = productBuilder;
        this.coverImageRepository = coverImageRepository;
        this.productImageRepository = productImageRepository;
    }

    public Product createProduct(ProductRequestDto productRequestDto) {
        if (productRequestDto.getQuantity() <=0)
            throw new BadRequestException("Quantity must be greater than 0");

        if (productRequestDto.getPrice() <=0)
            throw new BadRequestException("Price must be greater than 0");

        Product product = productBuilder.productRequestDtoToProduct(productRequestDto);
        List<Category> categories = productRequestDto.getCategoryId().stream().map(categoryService::getCategoryById).toList();
        Set<Category> productCategories = new HashSet<>();

        for (int i = 0;i<categories.size();i++) {
            if (categories.get(i).getSubCategories().isEmpty()) {
                productCategories.add(categories.get(i));
            }else
                throw new BadRequestException("Category is not a sub category: "+categories.get(i).getName());
        }
        product.setCategories(productCategories);
        return productRepository.save(product);
    }

    public Product updateProduct(int productId,ProductRequestDto productRequestDto) {
        if (productRequestDto.getQuantity() <=0)
            throw new BadRequestException("Quantity must be greater than 0");
        if (productRequestDto.getPrice() <=0)
            throw new BadRequestException("Price must be greater than 0");

        Product product = findById(productId);

        // sadece sub kategoriye eklenebilir
        List<Category> categories = productRequestDto.getCategoryId().stream().map(categoryService::getCategoryById).toList();
        Set<Category> productCategories = new HashSet<>();

        for (int i = 0;i<categories.size();i++) {
            if (categories.get(i).getSubCategories().isEmpty()) {
                productCategories.add(categories.get(i));
            }else
                throw new BadRequestException("Category is not a sub category: "+categories.get(i).getName());
        }
        product.setCategories(productCategories);
        product.setProductName(productRequestDto.getProductName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setQuantity(productRequestDto.getQuantity());
        product.setStatus(product.isStatus());

        productCategories.forEach(category -> {
            category.getProducts().add(product);
            categoryService.save(category);
        });

        return productRepository.save(product);
    }



    public List<Product> findAll() {
        return productRepository.findAll();
    }


    @Transactional
    public Product updateCoverImage(MultipartFile file, int id) {
        Product product = findById(id);
        CoverImage prodCoverImage = product.getCoverImage();

        if (prodCoverImage != null) {
            String s = imageService.deleteImage(product.getCoverImage());
            if (s.equals("deleted")){
                System.out.println("deleted category image id: "+prodCoverImage.getId());
                product.setCoverImage(null);
                productRepository.save(product);
                coverImageRepository.deleteById(prodCoverImage.getId());
            }
        }

        String path = ImageType.PRODUCT_COVER_IMAGE.getValue()+"/"+product.getId()+"/";
        String newPath = imageService.uploadImage(file,path);

        CoverImage coverImage = new CoverImage(newPath,ImageType.PRODUCT_COVER_IMAGE);
        CoverImage saveCoverImage = coverImageRepository.save(coverImage);
        product.setCoverImage(saveCoverImage);

        return productRepository.save(product);
    }



    public Product updateProductImage(MultipartFile[] files, int id) {
        Product product = findById(id);
        try {
            String path = ImageType.PRODUCT_IMAGE.getValue()+"/"+product.getId()+"/";
            for (MultipartFile file : files) {
                String newPath = imageService.uploadImage(file,path);

                ProductImage productImage = new ProductImage(newPath,0);
                ProductImage save = productImageRepository.save(productImage);
                product.getProductImages().add(save);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return productRepository.save(product);
    }

    @Transactional
    public String deleteProductImage(int prodId,List<Integer> imagesId) {
        Product product = findById(prodId);
        List<ProductImage> productImages = product.getProductImages();

        for (Integer imageId : imagesId) {
            ProductImage productImage = findImageById(imageId);

            if (productImages.contains(productImage)) {
                String ret = imageService.deleteImage(productImage);
                if (ret.equals("deleted")){
                    product.getProductImages().remove(productImage);
                    productRepository.save(product);
                    productImageRepository.delete(productImage);
                }
            }
        }

        return "Success Delete Image";
    }



    public Product findById(int id) {
        return productRepository.findById(id).orElseThrow(()-> new NotFoundException("Product not found"));
    }

    public ProductImage findImageById(int id) {
        return productImageRepository.findById(id).orElseThrow(()-> new NotFoundException("ProductImage not found"));
    }



    public String deleteProduct(int id) {
        Product product = findById(id);

        String s = imageService.deleteImage(product.getCoverImage());
        if (s.equals("deleted")){
            product.setCoverImage(null);
        }

        for (ProductImage productImage : product.getProductImages()) {
            String s1 = imageService.deleteImage(productImage);
            if (s1.equals("deleted")){
                product.getProductImages().remove(productImage);
                productRepository.save(product);
                productImageRepository.delete(productImage);
            }
        }

        productRepository.delete(product);
        return "Success Delete Product";

    }

    public List<ProductSmallResponseDto> getProductSmall() {
        return productRepository.findAll().stream().map(productBuilder::productToProductSmallResponseDto).collect(Collectors.toList());
    }

    public List<Product> getProductByQuantity(int quantity) {
        return productRepository.findAllByQuantityLessThan(quantity);
    }

    public List<ProductSmallResponseDto> getAllProductsByCategory(int categoryId) {
        Category category = categoryService.findById(categoryId);
        return productRepository.findAllByCategories(category).stream().map(productBuilder::productToProductSmallResponseDto).collect(Collectors.toList());
    }

    public Product updateCategory(int productId, List<Integer> categoryIds) {

        Product product = findById(productId);

        List<Category> categories = categoryIds.stream().map(categoryService::getCategoryById).toList();
        Set<Category> productCategories = new HashSet<>();

        for (int i = 0;i<categories.size();i++) {
            if (categories.get(i).getSubCategories().isEmpty()) {
                productCategories.add(categories.get(i));
            }else
                throw new BadRequestException("Category is not a sub category: "+categories.get(i).getName());
        }
        productCategories.forEach(category -> {
            category.getProducts().add(product);
            categoryService.save(category);
        });

        return product;
    }
}
