package com.cdac.service;

import com.cdac.entities.Order;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    @Override
    public List<Order> getAllOrders() {
        return List.of();
    }

    @Override
    public Order getOrderById(long id) {
        return null;
    }

    @Override
    public Order saveOrder(Order order) {
        return null;
    }

    @Override
    public void deleteOrder(long id) {

    }

    @Override
    public List<Order> getOrdersByUserId(long userId) {
        return List.of();
    }
}
