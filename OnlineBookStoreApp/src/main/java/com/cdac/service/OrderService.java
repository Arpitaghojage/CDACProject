package com.cdac.service;

import com.cdac.entities.Order;
import com.cdac.entities.OrderItem;

import java.util.List;

public interface OrderService {

    List<Order>getAllOrders();
    Order getOrderById(long id);
    Order saveOrder(Order order);
    void deleteOrder(long id);
    List<Order> getOrdersByUserId(long userId);


}
