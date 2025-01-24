package com.example.ecommerce.service.product;

import com.example.ecommerce.builder.product.CategoryBuilder;
import com.example.ecommerce.dto.product.CategoryRequestDto;
import com.example.ecommerce.dto.product.CategorySmallDto;
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
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;
    private final CoverImageRepository coverImageRepository;
    private final CategoryBuilder categoryBuilder;


    public CategoryService(CategoryRepository categoryRepository, ImageService imageService, CoverImageRepository coverImageRepository, CategoryBuilder categoryBuilder) {
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
        this.coverImageRepository = coverImageRepository;
        this.categoryBuilder = categoryBuilder;
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
        category.setHref(categoryRequestDto.getHref());
        category.setDescription(categoryRequestDto.getDescription());
        return categoryRepository.save(category);
    }

    public Category save(Category category) {
        if (category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Category name is required.");
        }
        return categoryRepository.save(category);
    }

    public List<CategorySmallDto> findPArentCategories() {
        List<Category> allByParentCategoryIsNull = categoryRepository.findAllByParentCategoryIsNull();
        return allByParentCategoryIsNull.stream()
                .map(categoryBuilder::buildCategory)
                .collect(Collectors.toList());

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


    public String delete(Integer categoryId) {
        // Kategori var mı diye kontrol et
        Category category = findById(categoryId);

        // Alt kategorileri recursive olarak sil
        deleteCategoryRecursively(category);

        return "Category and its subcategories successfully deleted.";
    }

    private void deleteCategoryRecursively(Category category) {
        // Alt kategoriler var mı kontrol et
        if (category.getSubCategories() != null && !category.getSubCategories().isEmpty()) {
            for (Category subCategory : category.getSubCategories()) {
                // Alt kategorileri recursive olarak sil
                deleteCategoryRecursively(subCategory);
            }
        }

        // Kapak resmini sil
        if (category.getCoverImage() != null) {
            imageService.deleteImage(category.getCoverImage());
        }

        // Ürün ilişkilerini sıfırla
        category.setProducts(null);

        // Ana kategoriyi veritabanından sil
        categoryRepository.delete(category);
    }
}
