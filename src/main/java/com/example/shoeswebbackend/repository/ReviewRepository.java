package com.example.shoeswebbackend.repository;

import com.example.shoeswebbackend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT rv FROM Review rv INNER JOIN Product p ON rv.product.id = :productId")
    List<Review> getReviewsByProductId(@Param("productId") Long productId);
}
