package com.cdac.repository;

import com.cdac.entities.Book;
import com.cdac.entities.Cart;
import com.cdac.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findByCart(Cart cart);
    List<CartItem> findByBook(Book book);
    CartItem findByCartAndBook(Cart cart, Book book);
    void deleteByCart(Cart cart);
}
