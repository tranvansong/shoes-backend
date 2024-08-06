package com.example.shoeswebbackend.service.impl;

import com.example.shoeswebbackend.entity.ProductSize;
import com.example.shoeswebbackend.repository.ProductSizeRepository;
import com.example.shoeswebbackend.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSizeServiceImpl implements ProductSizeService {

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Override
    public List<ProductSize> getAllProductSize() {
        return productSizeRepository.findAll();

    }

    @Override
    public ProductSize getProductSizeById(long id) {
        return productSizeRepository.findById(id).orElse(null);
    }
}
