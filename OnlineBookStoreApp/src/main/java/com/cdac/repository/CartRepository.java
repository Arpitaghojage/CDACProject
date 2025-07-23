package com.cdac.repository;

import com.cdac.entities.Cart;
import com.cdac.entities.CartItem;
import com.cdac.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem,Long> {

    Optional<Cart> findByUser(User user);
    boolean existsByUser(User user);
}
