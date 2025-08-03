package com.cdac.service;

import com.cdac.dto.OrderItemReqDTO;
import com.cdac.dto.OrderItemRespDTO;

import java.util.List;

public interface OrderItemService {

    List<OrderItemRespDTO> getAllOrderItems();
    OrderItemRespDTO getOrderItemById(Long id);
    OrderItemRespDTO saveOrderItem(OrderItemReqDTO orderItemDto);
    void deleteOrderItem(long id);

}
