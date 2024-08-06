package com.example.shoeswebbackend.service;

import com.example.shoeswebbackend.dto.ProductDTO;
import com.example.shoeswebbackend.dto.response.ProductResponse;
import com.example.shoeswebbackend.exception.AppException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getAllProducts(List<String> brands, List<String> categories, List<String> sizes, float minPrice, float maxPrice, String keyword);
    List<ProductResponse> getProductsPopular();
    ProductResponse getProductById(long id) throws AppException;
    ProductResponse getProductByProductCode(String productCode) throws AppException;
    List<ProductResponse> getProductsByCategory(String category);
    List<ProductResponse> getProductsByBrand(String brand);
    void createProduct(ProductDTO productDTO, MultipartFile[] images) throws AppException;
    void updateProduct(ProductDTO productDTO, MultipartFile[] images, String code) throws AppException;
    void deleteProduct(long id);
}
