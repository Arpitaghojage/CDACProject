package com.cdac.controller;

import com.cdac.dto.CartReqDTO;
import com.cdac.dto.CartRespDTO;
import com.cdac.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartRespDTO>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartRespDTO> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartById(id));
    }

    @PostMapping
    public ResponseEntity<CartRespDTO> addCart(@RequestBody CartReqDTO cartDto) {
        return ResponseEntity.ok(cartService.addCart(cartDto));
    }

    @PutMapping
    public ResponseEntity<CartRespDTO> updateCart(@RequestBody CartReqDTO cartDto) {
        return ResponseEntity.ok(cartService.updateCart(cartDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        cartService.deleteCartItem(id);
        return ResponseEntity.ok("Cart deleted successfully");
    }
}
