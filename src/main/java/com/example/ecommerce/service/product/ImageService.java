package com.example.ecommerce.service.product;

import com.example.ecommerce.entity.product.Image;
import com.example.ecommerce.entity.product.ImageType;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.product.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {
    @Value("${upload.dir}")
    private String uploadDir;  // /var/www/upload/
    @Value("${upload.url}")
    private String upluadUrl; // http://localhost:8080/
    @Value("${upload.file.url}")
    private String uploadFileUrl;   // http://localhost:8080/api/upload/

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image uploadImage(MultipartFile file, String paths, ImageType imageType) {
        String newPath = uploadDir + paths;
        System.out.println("newPath: "+ newPath);
        Path path = Paths.get(newPath);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isEmpty()) {
                throw new RuntimeException("File name is invalid");
            }

            String fileExtension = "";
            int lastIndexOfDot = originalFileName.lastIndexOf(".");
            if (lastIndexOfDot != -1) {
                fileExtension = originalFileName.substring(lastIndexOfDot); // Örneğin ".jpg"
            }

            String newFileName = UUID.randomUUID() + fileExtension;

            Path filePath = path.resolve(newFileName);

            // Aynı isimde bir dosya var mı kontrol et
            if (Files.exists(filePath)) {
                throw new RuntimeException("A file with the name '" + originalFileName + "' already exists.");
            }

            String urls = uploadFileUrl+paths+newFileName;
            System.out.println("urls: "+ urls);

            Image image = new Image(newFileName,urls,imageType);
            imageRepository.save(image);
            file.transferTo(filePath.toFile());

            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteImage(int imageId) {
        Image image = getImageById(imageId);

        String imageUrl = image.getUrl();   // http://localhost:8080/api/upload/images/6e07c4f8-a3c6-4c65-92f3-84190b2e1f76.jpg
        String path = imageUrl.replace(uploadFileUrl,uploadDir);

        System.out.println("path: "+ path);

        File file = new File(path);
        // Dosya mevcut değilse hata fırlat
        if (!file.exists()) {
            throw new NotFoundException("File not found: " + path);
        }

        if (file.delete()) { // Dosya silinir.
            imageRepository.delete(image);
            return "deleted: "+ image.getUrl();
        } else {
            throw new RuntimeException("Failed to delete file: " + image.getUrl());
        }

    }


    public Image getImageById(int imageId) {
        return imageRepository
                .findById(imageId)
                .orElseThrow(()-> new NotFoundException("Image not found"));
    }


    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}
