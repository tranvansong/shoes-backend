package com.example.shoeswebbackend.utils;

import com.example.shoeswebbackend.dto.*;
import com.example.shoeswebbackend.dto.response.ProductResponse;
import com.example.shoeswebbackend.dto.response.ReviewResponse;
import com.example.shoeswebbackend.dto.response.UserResponse;
import com.example.shoeswebbackend.entity.*;
import com.example.shoeswebbackend.repository.BrandRepository;
import com.example.shoeswebbackend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    public CategoryDTO convertCategoryToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public Category convertDTOToCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }

    public BrandDTO covertBrandToDTO(Brand brand) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(brand.getId());
        brandDTO.setName(brand.getName());
        return brandDTO;
    }

    public Brand convertDTOToBrand(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setId(brandDTO.getId());
        brand.setName(brandDTO.getName());
        return brand;
    }

    public RoleDTO convertRoleToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    public Role convertDTOToRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        return role;
    }

    public Product convertDTOToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setProductCode(productDTO.getProductCode());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock_quantity(productDTO.getStock_quantity());
        product.setAverage_rating(productDTO.getAverage_rating());
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElse(null));
        product.setBrand(brandRepository.findById(productDTO.getBrandId()).orElse(null));
        return product;
    }

    public ProductResponse convertProductToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setProductCode(product.getProductCode());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setStock_quantity(product.getStock_quantity());
        productResponse.setSold_quantity(product.getSold_quantity());
        productResponse.setAverage_rating(product.getAverage_rating());

        productResponse.setCategory(product.getCategory().getName());

        productResponse.setBrand(product.getBrand().getName());

        List<String> imageUrls = product.getProductImages().stream()
                .map(ProductImage::getImage_url)
                .toList();
        productResponse.getImageUrls().addAll(imageUrls);
        System.out.println(productResponse);
        return productResponse;
    }

    public ReviewResponse convertReviewToReviewResponse(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setName(review.getUser().getEmail());
        reviewResponse.setComment(review.getComment());
        reviewResponse.setRating(review.getRating());
        reviewResponse.setCreated_at(review.getCreated_at());
        reviewResponse.setUpdated_at(review.getUpdated_at());
        return reviewResponse;
    }

    public UserResponse convertUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFullName(user.getFull_name());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setAddress(user.getAddress());
        userResponse.setPhone(user.getPhone_number());
        userResponse.setAvatar(user.getAvatar());
        userResponse.setStatus(user.getStatus());
        userResponse.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return userResponse;
    }

    public User convertDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFull_name(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPhone_number(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
        user.setStatus(UserStatus.OFFLINE);
        return user;
    }

//    public ProductVariant convertProductVariantRequestToProductVariant(ProductVariantRequest productVariantRequest) {
//        ProductVariant productVariant = new ProductVariant();
//        productVariant.setProductSize(productVariant.gry);
//    }
}
