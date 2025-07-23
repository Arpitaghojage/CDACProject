package com.cdac.service;

import com.cdac.entities.OrderItem;

import java.util.List;

public class OrderItemServiceImpl implements OrderItemService {

    @Override
    public List<OrderItem> getAllOrderItems() {
        return List.of();
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return null;
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return null;
    }

    @Override
    public void deleteOrderItem(long id) {

    }

    @Override
    public List<OrderItem> getOrdersByUserId(Long userId) {
        return List.of();
    }
}
