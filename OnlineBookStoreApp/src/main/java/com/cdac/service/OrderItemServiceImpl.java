package com.cdac.service;

import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dto.OrderItemReqDTO;
import com.cdac.dto.OrderItemRespDTO;
import com.cdac.entities.Order;
import com.cdac.entities.OrderItem;
import com.cdac.entities.User;
import com.cdac.repository.OrderItemRepository;
import com.cdac.repository.OrderRepository;
import com.cdac.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private ModelMapper modelMapper;

    @Override
    public List<OrderItemRespDTO> getAllOrderItems() {
        return orderItemRepository.findAll()
                .stream()
                .map(item -> modelMapper.map(item, OrderItemRespDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemRespDTO getOrderItemById(Long id) {
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with ID: " + id));
        return modelMapper.map(item, OrderItemRespDTO.class);
    }

    @Override
    public OrderItemRespDTO saveOrderItem(OrderItemReqDTO orderItemDto) {
        Order order = orderRepository.findById(orderItemDto.getOrder().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderItemDto.getOrder().getId()));

        OrderItem item = modelMapper.map(orderItemDto, OrderItem.class);
        item.setOrder(order);
        OrderItem saved = orderItemRepository.save(item);

        return modelMapper.map(saved, OrderItemRespDTO.class);
    }

    @Override
    public void deleteOrderItem(long id) {
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with ID: " + id));
        orderItemRepository.delete(item);
    }

}
