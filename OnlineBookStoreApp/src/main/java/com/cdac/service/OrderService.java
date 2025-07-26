package com.cdac.service;

import com.cdac.dto.OrderReqDTO;
import com.cdac.dto.OrderRespDTO;
import com.cdac.entities.Order;
import com.cdac.entities.OrderItem;

import java.util.List;

public interface OrderService {

    List<OrderRespDTO>getAllOrders();
    OrderRespDTO getOrderById(Long id);
    OrderRespDTO saveOrder(OrderReqDTO orderDto);
    void deleteOrder(Long id);
    List<OrderRespDTO> getOrdersByUserId(Long userId);

}
