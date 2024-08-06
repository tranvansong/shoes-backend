package com.example.shoeswebbackend.service.impl;

import com.example.shoeswebbackend.dto.ProductVariantRequest;
import com.example.shoeswebbackend.dto.response.ProductVariantResponse;
import com.example.shoeswebbackend.exception.AppException;
import com.example.shoeswebbackend.repository.ProductVariantRepository;
import com.example.shoeswebbackend.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Override
    public List<ProductVariantResponse> getAllProductVariants() {
        return productVariantRepository.getAllProducts();
    }

    @Override
    public List<ProductVariantResponse> getAllProductVariantsByProductId(Long productId) {
        return productVariantRepository.getProductVariantsByProductId(productId);
    }

    @Override
    public void saveProductVariant(ProductVariantRequest productVariantRequest) throws AppException {
//        return productVariantRepository.save(productVariantRequest);
    }

}
