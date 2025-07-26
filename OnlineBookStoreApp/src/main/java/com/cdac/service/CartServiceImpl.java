package com.cdac.service;

import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dto.CartReqDTO;
import com.cdac.dto.CartRespDTO;
import com.cdac.entities.Cart;
import com.cdac.entities.User;
import com.cdac.repository.CartRepository;
import com.cdac.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CartServiceImpl  implements CartService{

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private ModelMapper modelMapper;


    @Override
    public List<CartRespDTO> getAllCarts() {
        return cartRepository.findAll()
                .stream()
                .map(cart -> modelMapper.map(cart, CartRespDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CartRespDTO getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id " + id));
        return modelMapper.map(cart, CartRespDTO.class);
    }

    @Override
    public CartRespDTO addCart(CartReqDTO cartDto) {
        User user = userRepository.findById(cartDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + cartDto.getUserId()));

        Cart cart = new Cart();
        cart.setUser(user);

        Cart saved = cartRepository.save(cart);
        return modelMapper.map(saved, CartRespDTO.class);
    }

    @Override
    public CartRespDTO updateCart(CartReqDTO cartDto) {
        Cart existing = cartRepository.findById(cartDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: " + cartDto.getUserId()));

        User user = userRepository.findById(cartDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + cartDto.getUserId()));

        existing.setUser(user);

        Cart updated = cartRepository.save(existing);
        return modelMapper.map(updated, CartRespDTO.class);
    }

    @Override
    public void deleteCartItem(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id " + id));
        cartRepository.deleteById(id);
    }
}
