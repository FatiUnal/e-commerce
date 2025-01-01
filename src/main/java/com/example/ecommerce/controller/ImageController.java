package com.example.ecommerce.controller;

import com.example.ecommerce.entity.product.Image;
import com.example.ecommerce.entity.product.ImageType;
import com.example.ecommerce.service.product.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<Image> addImage(@RequestParam("file") MultipartFile file,@RequestParam String paths) {  // postimages/1/
        return new ResponseEntity<>(imageService.uploadImage(file,paths,ImageType.PRODUCT_IMAGE), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteImage(@RequestParam("id") int id) {
        return new ResponseEntity<>(imageService.deleteImage(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        return new ResponseEntity<>(imageService.getAllImages(),HttpStatus.OK);
    }

    @GetMapping("by-id")
    public ResponseEntity<Image> getImageById(@RequestParam("id") int id) {
        return new ResponseEntity<>(imageService.getImageById(id),HttpStatus.OK);
    }



}
