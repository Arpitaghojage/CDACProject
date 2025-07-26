package com.cdac.service;

import com.cdac.dto.CartItemReqDTO;
import com.cdac.dto.CartItemRespDTO;
import com.cdac.dto.CartReqDTO;
import com.cdac.dto.CartRespDTO;
import com.cdac.entities.Cart;
import com.cdac.entities.CartItem;

import java.util.List;

public interface CartItemService {

    List<CartItemRespDTO> getAllCartItems();
    CartItemRespDTO getCartItemById(Long id);
    CartItemRespDTO saveItem(CartItemReqDTO cartItemDto);
    CartItemRespDTO updateCartItem(CartItemReqDTO cartItemDto);
    void deleteCartItem(Long id);
}
