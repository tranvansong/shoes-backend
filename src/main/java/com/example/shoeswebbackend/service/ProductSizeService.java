package com.example.shoeswebbackend.service;

import com.example.shoeswebbackend.entity.ProductSize;

import java.util.List;

public interface ProductSizeService {
    List<ProductSize> getAllProductSize();
    ProductSize getProductSizeById(long id);
}
