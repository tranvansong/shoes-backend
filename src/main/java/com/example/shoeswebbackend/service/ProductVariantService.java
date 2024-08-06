package com.example.shoeswebbackend.service;

import com.example.shoeswebbackend.dto.ProductVariantRequest;
import com.example.shoeswebbackend.dto.response.ProductVariantResponse;
import com.example.shoeswebbackend.exception.AppException;

import java.util.List;

public interface ProductVariantService {
    List<ProductVariantResponse> getAllProductVariants();
    List<ProductVariantResponse> getAllProductVariantsByProductId(Long productId);
    void saveProductVariant(ProductVariantRequest productVariantRequest) throws AppException;
}
