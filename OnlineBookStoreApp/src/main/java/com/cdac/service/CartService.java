package com.cdac.service;


import com.cdac.dto.CartReqDTO;
import com.cdac.dto.CartRespDTO;
import com.cdac.entities.Cart;

import java.util.List;

public interface CartService {

    List<CartRespDTO> getAllCarts();
    CartRespDTO getCartById(Long id);
    CartRespDTO addCart(CartReqDTO cartDto);
    CartRespDTO updateCart(CartReqDTO cartDto);
    void deleteCartItem(Long id);
}
