package com.example.shoeswebbackend.repository;

import com.example.shoeswebbackend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
