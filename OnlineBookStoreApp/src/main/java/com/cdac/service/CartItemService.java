package com.cdac.service;

import com.cdac.entities.Cart;
import com.cdac.entities.CartItem;

import java.util.List;

public interface CartItemService {

    List<CartItem> getAllCartItems();
    CartItem getCartItemById(Long id);
    CartItem saveItem(CartItem cartItem);
    Cart addCartItem(Cart cart);
    Cart updateCartItem(Cart cart);
    void deleteCartItem(Long id);
}
