package com.example.shoeswebbackend.service.impl;

import com.example.shoeswebbackend.dto.OrderItemRequest;
import com.example.shoeswebbackend.dto.OrderRequest;
import com.example.shoeswebbackend.entity.Order;
import com.example.shoeswebbackend.entity.ProductVariant;
import com.example.shoeswebbackend.exception.OutOfStockException;
import com.example.shoeswebbackend.repository.OrderRepository;
import com.example.shoeswebbackend.repository.ProductVariantRepository;
import com.example.shoeswebbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Override
    @Transactional(rollbackFor = OutOfStockException.class)
    public void saveOrder(OrderRequest orderRequest) throws OutOfStockException {
        // Kiểm tra tồn kho cho từng sản phẩm trong đơn hàng
        for (OrderItemRequest item : orderRequest.getOrderItemsRequest()) {
            ProductVariant variant = productVariantRepository.findById(item.getVariantId())
                    .orElseThrow(() -> new OutOfStockException("Variant không tồn tại"));

            if (variant.getStock_quantity() < item.getQuantity()) {
                throw new OutOfStockException("Sản phẩm " + item.getProductName() + " không đủ hàng.");
            }

            // Giảm số lượng tồn kho
            variant.setStock_quantity(variant.getStock_quantity() - item.getQuantity());
            productVariantRepository.save(variant);
        }

        Order order = maptoOrder(orderRequest);
        System.out.println(order);
//        orderRepository.save(order);
    }

    private Order maptoOrder(OrderRequest orderRequest) {
        Order order = new Order();
        return order;
    }
}
