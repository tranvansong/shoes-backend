package com.example.shoeswebbackend.repository;

import com.example.shoeswebbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.category.name = :name")
    List<Product> findByCategoryName(@Param("name") String categoryName);

    @Query("SELECT p FROM Product p WHERE p.brand.name = :name")
    List<Product> findByBrandName(@Param("name") String brandName);

    @Query("SELECT p FROM Product p WHERE p.productCode = :code")
    Product findByProductCode(@Param("code") String productCode);
    @Query("SELECT p FROM Product p WHERE "
            + "(:brands IS NULL OR p.brand.name IN :brands) AND "
            + "(:categories IS NULL OR p.category.name IN :categories) AND "
            + "(:sizes IS NULL OR EXISTS (SELECT pv FROM ProductVariant pv WHERE pv.product = p AND pv.productSize.size IN :sizes)) AND "
            + "(p.price >= :minPrice AND p.price <= :maxPrice) AND "
            + "(:keyword IS NULL OR p.name LIKE %:keyword%)")
    List<Product> findAllWithFilters(
            @Param("brands") List<String> brands,
            @Param("categories") List<String> categories,
            @Param("sizes") List<String> sizes,
            @Param("minPrice") float minPrice,
            @Param("maxPrice") float maxPrice,
            @Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.sold_quantity IS NOT NULL ORDER BY p.sold_quantity DESC LIMIT 4")
    List<Product> findTopSellingProducts();
}
