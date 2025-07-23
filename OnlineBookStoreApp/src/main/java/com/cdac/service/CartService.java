package com.cdac.service;

import com.cdac.entities.Cart;

import java.util.List;

public interface CartService {

    List<Cart> getAllCarts();
    Cart getCartById(Long id);
    Cart addCart(Cart cart);
    Cart updateCart(Cart cart);
    Cart deleteCart(Long id);




}
