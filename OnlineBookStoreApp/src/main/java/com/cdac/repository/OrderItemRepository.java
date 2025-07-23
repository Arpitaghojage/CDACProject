package com.cdac.repository;

import com.cdac.entities.Order;
import com.cdac.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    List<OrderItem> findByOrder(Order order);
}
