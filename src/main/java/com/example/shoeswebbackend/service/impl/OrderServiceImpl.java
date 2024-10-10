package com.example.shoeswebbackend.service.impl;

import com.example.shoeswebbackend.dto.OrderRequest;
import com.example.shoeswebbackend.entity.Order;
import com.example.shoeswebbackend.repository.OrderRepository;
import com.example.shoeswebbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void saveOrder(OrderRequest orderRequest) {
        System.out.println(orderRequest);
    }

    private Order maptoOrder(OrderRequest orderRequest) {
        Order order = new Order();
        return order;
    }
}
