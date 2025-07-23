package com.cdac.service;

import com.cdac.entities.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> getAllOrderItems();
    OrderItem getOrderItemById(Long id);
    OrderItem saveOrderItem(OrderItem orderItem);
    void deleteOrderItem(long id);
    List<OrderItem> getOrdersByUserId(Long userId);

}
