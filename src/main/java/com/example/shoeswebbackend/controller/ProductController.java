package com.example.shoeswebbackend.controller;

import com.example.shoeswebbackend.dto.ProductDTO;
import com.example.shoeswebbackend.dto.response.ProductResponse;
import com.example.shoeswebbackend.dto.ReviewForm;
import com.example.shoeswebbackend.exception.AppException;
import com.example.shoeswebbackend.service.ProductService;
import com.example.shoeswebbackend.service.ProductVariantService;
import com.example.shoeswebbackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductVariantService productVariantService;

    @GetMapping({"/", ""})
    public ResponseEntity<?> getAllProducts(@RequestParam(name = "category", defaultValue = "") String categoryName,
                                            @RequestParam(name ="brand", defaultValue = "") String brandName,
                                            @RequestParam(name = "brands", required = false) List<String> brands,
                                            @RequestParam(name = "categories", required = false) List<String> categories,
                                            @RequestParam(name = "sizes", required = false) List<String> sizes,
                                            @RequestParam(name = "minPrice", defaultValue = "0") float minPrice,
                                            @RequestParam(name = "maxPrice", defaultValue = "100000000") float maxPrice,
                                            @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        List<ProductResponse> productResponses;
        if ((brands == null || brands.isEmpty()) &&
                (categories == null || categories.isEmpty()) &&
                (sizes == null || sizes.isEmpty()) &&
                minPrice == 0 &&
                maxPrice == 100000000 &&
                keyword.isEmpty() &&
                categoryName.isEmpty() &&
                brandName.isEmpty()) {
            productResponses = productService.getAllProducts();
        } else if (!categoryName.isEmpty()) {
            productResponses = productService.getProductsByCategory(categoryName);
        } else if (!brandName.isEmpty()) {
            productResponses = productService.getProductsByBrand(brandName);
        } else {
            productResponses = productService.getAllProducts(brands, categories, sizes, minPrice, maxPrice, keyword);
        }

        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getProductsPopular() {
        return ResponseEntity.ok(productService.getProductsPopular());
    }


    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestPart("product") ProductDTO productDTO,
                                           @RequestPart("images") MultipartFile[] images) {
        System.out.println(productDTO);
        try {
            productService.createProduct(productDTO, images);
        } catch (AppException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully");
    }

    @PutMapping("/update/{code}")
    public ResponseEntity<?> updateProduct(@RequestPart("product") ProductDTO productDTO,
                                           @RequestPart(name = "images", required = false) MultipartFile[] images,
                                           @PathVariable("code") String code) {
        System.out.println(productDTO);
        try {
            productService.updateProduct(productDTO, images, code);
        } catch (AppException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Product updated successfully");
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getProductByProductCode(@PathVariable("code") String code) {
        try {
            ProductResponse productResponse = productService.getProductByProductCode(code);
            return ResponseEntity.ok(productResponse);
        } catch (AppException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/reviews")
    public ResponseEntity<?> getReviewsByProductId(@RequestParam("productId") long id) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(id));
    }

    @PostMapping("/reviews")
    public ResponseEntity<?> addReviewToProduct(@RequestBody ReviewForm reviewForm) {
        reviewService.addReviewToProduct(reviewForm);
        return ResponseEntity.status(HttpStatus.CREATED).body("Review added successfully");
    }

    @GetMapping("/product-variants")
    public ResponseEntity<?> getAllProductVariants() {
        return ResponseEntity.ok(productVariantService.getAllProductVariants());
    }

    @GetMapping("/product-variants/{productId}")
    public ResponseEntity<?> getAllProductVariantsByProductId(@PathVariable("productId") long productId) {
        return ResponseEntity.ok(productVariantService.getAllProductVariantsByProductId(productId));
    }

}
