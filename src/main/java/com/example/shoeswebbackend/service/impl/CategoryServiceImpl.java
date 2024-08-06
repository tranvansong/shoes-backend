package com.example.shoeswebbackend.service.impl;

import com.example.shoeswebbackend.dto.CategoryDTO;
import com.example.shoeswebbackend.entity.Category;
import com.example.shoeswebbackend.exception.AppException;
import com.example.shoeswebbackend.repository.CategoryRepository;
import com.example.shoeswebbackend.service.CategoryService;
import com.example.shoeswebbackend.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(mapper::convertCategoryToDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findById(long id) throws AppException {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new AppException(404, "Category not found");
        }
        return mapper.convertCategoryToDTO(category);
    }

    @Override
    public void save(CategoryDTO categoryDTO) throws AppException {
        if(categoryRepository.findByName(categoryDTO.getName()) != null) {
            throw new AppException(409, "Category already exists");
        }
        Category category = mapper.convertDTOToCategory(categoryDTO);
        categoryRepository.save(category);
    }

    @Override
    public void update(CategoryDTO categoryDTO, long id) throws AppException {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new AppException(404, "Category not found");
        }
        Category categoryExists = categoryRepository.findByName(categoryDTO.getName());
        if(categoryExists != null && !categoryExists.getId().equals(category.getId())) {
            throw new AppException(409, "Category already exists");
        }
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(long id) {
        categoryRepository.deleteById(id);
    }
}
