package com.example.shoeswebbackend.service;

import com.example.shoeswebbackend.dto.BrandDTO;
import com.example.shoeswebbackend.entity.Brand;
import com.example.shoeswebbackend.exception.AppException;

import java.util.List;

public interface BrandService {
    List<BrandDTO> getAllBrands();
    BrandDTO getBrandById(long id) throws AppException;
    void saveBrand(BrandDTO brandDTO) throws AppException;
    void updateBrand(BrandDTO brandDTO, long id) throws AppException;
    void deleteBrand(long id);
}
