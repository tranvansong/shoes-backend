package com.example.shoeswebbackend.repository;

import com.example.shoeswebbackend.dto.response.ProductVariantResponse;
import com.example.shoeswebbackend.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    @Query("SELECT new com.example.shoeswebbackend.dto.response.ProductVariantResponse(" +
            "pv.id, ps.size, pv.stock_quantity) " +
            "FROM ProductVariant pv " +
            "JOIN pv.product p " +
            "JOIN pv.productSize ps " +
            "ON p.id = pv.product.id")
    List<ProductVariantResponse> getAllProducts();


    @Query("SELECT new com.example.shoeswebbackend.dto.response.ProductVariantResponse(" +
            "pv.id, ps.size, pv.stock_quantity) " +
            "FROM ProductVariant pv " +
            "JOIN pv.product p " +
            "JOIN pv.productSize ps " +
            "WHERE p.id = :productId")
    List<ProductVariantResponse> getProductVariantsByProductId(@Param("productId") Long productId);

}
