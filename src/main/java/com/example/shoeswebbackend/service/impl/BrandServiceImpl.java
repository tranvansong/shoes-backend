package com.example.shoeswebbackend.service.impl;

import com.example.shoeswebbackend.dto.BrandDTO;
import com.example.shoeswebbackend.entity.Brand;
import com.example.shoeswebbackend.exception.AppException;
import com.example.shoeswebbackend.repository.BrandRepository;
import com.example.shoeswebbackend.service.BrandService;
import com.example.shoeswebbackend.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll().stream().map(mapper::covertBrandToDTO).collect(Collectors.toList());
    }

    @Override
    public BrandDTO getBrandById(long id) throws AppException {
        Brand brand = brandRepository.findById(id).orElse(null);
        if (brand == null) {
            throw new AppException(404, "Brand not found");
        }
        return mapper.covertBrandToDTO(brand);
    }

    @Override
    public void saveBrand(BrandDTO brandDTO) throws AppException {
        if(brandRepository.findByName(brandDTO.getName()) != null) {
            throw new AppException(409, "Brand already exists");
        }
        brandRepository.save(mapper.convertDTOToBrand(brandDTO));
    }

    @Override
    public void updateBrand(BrandDTO brandDTO, long id) throws AppException {
        Brand brand = brandRepository.findById(id).orElse(null);
        if (brand == null) {
            throw new AppException(404, "Brand not found");
        }
        brand.setName(brandDTO.getName());
        brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(long id) {
        brandRepository.deleteById(id);
    }
}
