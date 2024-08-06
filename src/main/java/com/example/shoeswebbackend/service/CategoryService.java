package com.example.shoeswebbackend.service;

import com.example.shoeswebbackend.dto.CategoryDTO;
import com.example.shoeswebbackend.exception.AppException;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAll();

    CategoryDTO findById(long id) throws AppException;

    void save(CategoryDTO categoryDTO) throws AppException;

    void update(CategoryDTO categoryDTO, long id) throws AppException;

    void deleteById(long id);

}
