package com.cdac.service;

import com.cdac.dto.CartItemReqDTO;
import com.cdac.dto.CartItemRespDTO;

import java.util.List;

public interface CartItemService {

    List<CartItemRespDTO> getAllCartItems();
    CartItemRespDTO getCartItemById(Long id);
    CartItemRespDTO saveItem(CartItemReqDTO cartItemDto);
    CartItemRespDTO updateCartItem(CartItemReqDTO cartItemDto);
    void deleteCartItem(Long id);
}
