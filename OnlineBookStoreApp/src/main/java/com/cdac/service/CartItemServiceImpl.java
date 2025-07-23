package com.cdac.service;

import com.cdac.entities.Cart;
import com.cdac.entities.CartItem;

import java.util.List;

public class CartItemServiceImpl implements CartItemService {


    @Override
    public List<CartItem> getAllCartItems() {
        return List.of();
    }

    @Override
    public CartItem getCartItemById(Long id) {
        return null;
    }

    @Override
    public CartItem saveItem(CartItem cartItem) {
        return null;
    }

    @Override
    public Cart addCartItem(Cart cart) {
        return null;
    }

    @Override
    public Cart updateCartItem(Cart cart) {
        return null;
    }

    @Override
    public void deleteCartItem(Long id) {

    }
}
