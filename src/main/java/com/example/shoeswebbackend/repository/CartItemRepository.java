package com.example.shoeswebbackend.repository;

import com.example.shoeswebbackend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
