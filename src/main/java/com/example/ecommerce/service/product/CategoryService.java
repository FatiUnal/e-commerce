package com.example.ecommerce.service.product;

import com.example.ecommerce.builder.product.CategoryBuilder;
import com.example.ecommerce.dto.product.CategoryRequestDto;
import com.example.ecommerce.entity.product.Category;
import com.example.ecommerce.entity.product.image.CoverImage;
import com.example.ecommerce.entity.product.image.ImageType;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.product.CategoryRepository;
import com.example.ecommerce.repository.product.Image.CoverImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;
    private final CoverImageRepository coverImageRepository;


    public CategoryService(CategoryRepository categoryRepository, ImageService imageService, CoverImageRepository coverImageRepository) {
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
        this.coverImageRepository = coverImageRepository;
    }


    public Category save(CategoryRequestDto categoryRequestDto) {
        if (categoryRequestDto.getName() == null || categoryRequestDto.getName().isBlank()) {
            throw new IllegalArgumentException("Category name is required.");
        }

        categoryRepository
                .existsByNameEqualsIgnoreCase(categoryRequestDto.getName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category name."));


        Category category = new Category();
        if (categoryRequestDto.getParentCategoryId() != 0 ) {
            Category parentCategory = findById(categoryRequestDto.getParentCategoryId());
            category.setParentCategory(parentCategory);
        }

        category.setName(categoryRequestDto.getName());
        category.setDescription(categoryRequestDto.getDescription());
        return categoryRepository.save(category);
    }

    public List<Category> findPArentCategories() {
        return categoryRepository.findAllByParentCategoryIsNull();
    }

    public Category findById(int id) {
        return categoryRepository.
                findById(id).orElseThrow(() -> new NotFoundException("Category Not Found"));
    }


    public Category getCategoryById(Integer categoryId) {
        return findById(categoryId);
    }


    @Transactional
    public Category updateCoverImage(MultipartFile file, int id) {
        Category category = findById(id);
        CoverImage coverImages = category.getCoverImage();

        if (coverImages != null) {
            String s = imageService.deleteImage(category.getCoverImage());
            if (s.equals("deleted")){
                System.out.println("deleted category image id: "+coverImages.getId());
                category.setCoverImage(null);
                categoryRepository.save(category);
                coverImageRepository.deleteById(coverImages.getId());
            }
        }

        String path = ImageType.CATEGORY_IMAGE.getValue()+"/"+category.getId()+"/";
        String newPath = imageService.uploadImage(file, path);
        CoverImage coverImage = new CoverImage(newPath,ImageType.CATEGORY_IMAGE);
        CoverImage saveCoverImage = coverImageRepository.save(coverImage);
        category.setCoverImage(saveCoverImage);
        return categoryRepository.save(category);
    }

    public boolean isParentCategory(Category category,Category parentCategory) {
        if (category == null || parentCategory == null) {
            return false; // Null kontrolü
        }

        // Geçerli kategoriye kadar iterasyon yap
        Category currentCategory = category.getParentCategory();
        while (currentCategory != null) {
            if (currentCategory.equals(parentCategory)) {
                return true; // Ebeveyn bulundu
            }
            currentCategory = currentCategory.getParentCategory(); // Bir üst seviyeye geç
        }

        return false; // Ebeveyn bulunamadı
    }
    public boolean isSubCategory(Category category, Category subCategory) {
        if (category == null || subCategory == null) {
            return false; // Null kontrolü
        }

        // Alt kategorilerde gezinerek kontrol et
        for (Category sub : category.getSubCategories()) {
            if (sub.equals(subCategory) || isSubCategory(sub, subCategory)) {
                return true; // Alt kategori bulundu
            }
        }

        return false; // Alt kategori bulunamadı
    }






}
