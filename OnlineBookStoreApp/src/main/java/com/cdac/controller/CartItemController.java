package com.cdac.controller;

import com.cdac.dto.CartItemReqDTO;
import com.cdac.dto.CartItemRespDTO;
import com.cdac.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
@AllArgsConstructor
@Validated
public class CartItemController {

    private final CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<List<CartItemRespDTO>> getAllCartItems() {
        return ResponseEntity.ok(cartItemService.getAllCartItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemRespDTO> getCartItemById(@PathVariable Long id) {
        return ResponseEntity.ok(cartItemService.getCartItemById(id));
    }

    @PostMapping
    public ResponseEntity<CartItemRespDTO> saveCartItem(@RequestBody CartItemReqDTO cartItemDto) {
        return ResponseEntity.ok(cartItemService.saveItem(cartItemDto));
    }

    @PutMapping
    public ResponseEntity<CartItemRespDTO> updateCartItem(@RequestBody CartItemReqDTO cartItemDto) {
        return ResponseEntity.ok(cartItemService.updateCartItem(cartItemDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok("Cart item deleted successfully");
    }
}
