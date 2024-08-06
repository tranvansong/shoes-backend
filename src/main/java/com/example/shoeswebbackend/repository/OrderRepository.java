package com.example.shoeswebbackend.repository;

import com.example.shoeswebbackend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
