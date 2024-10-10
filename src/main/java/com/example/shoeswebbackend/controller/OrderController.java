package com.example.shoeswebbackend.controller;

import com.example.shoeswebbackend.dto.OrderRequest;
import com.example.shoeswebbackend.dto.response.OrderResponse;
import com.example.shoeswebbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> saveOrder(@RequestBody OrderRequest orderRequest) {
        orderService.saveOrder(orderRequest);
        return ResponseEntity.ok().body("Order created successfully");
    }
}
