package com.cdac.repository;

import com.cdac.entities.Order;
import com.cdac.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUser(User user);
    List<Order> findByUserOrderByOrderDateDesc(User user);
}
