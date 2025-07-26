package com.cdac.service;

import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dto.OrderReqDTO;
import com.cdac.dto.OrderRespDTO;
import com.cdac.entities.Order;
import com.cdac.entities.User;
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
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public List<OrderRespDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderRespDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderRespDTO getOrderById(Long id) {
         Order order = orderRepository.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
         return modelMapper.map(order, OrderRespDTO.class);
    }

    @Override
    public OrderRespDTO saveOrder(OrderReqDTO orderDto) {
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + orderDto.getUserId()));

        Order order = modelMapper.map(orderDto, Order.class);
        order.setUser(user);

        Order saved = orderRepository.save(order);
        return modelMapper.map(saved, OrderRespDTO.class);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderRespDTO> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        return orderRepository.findByUser(user)
                .stream()
                .map(order -> modelMapper.map(order, OrderRespDTO.class))
                .collect(Collectors.toList());
    }
}
