package com.cdac.service;

import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dto.CartItemReqDTO;
import com.cdac.dto.CartItemRespDTO;
import com.cdac.entities.Book;
import com.cdac.entities.Cart;
import com.cdac.entities.CartItem;
import com.cdac.repository.BookRepository;
import com.cdac.repository.CartItemRepository;
import com.cdac.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private CartItemRepository cartItemRepository;
    private CartRepository cartRepository;
    private BookRepository bookRepository;
    private ModelMapper modelMapper;

    @Override
    public List<CartItemRespDTO> getAllCartItems() {
        return cartItemRepository.findAll()
                .stream()
                .map(item -> modelMapper.map(item, CartItemRespDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CartItemRespDTO getCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("cartItem not found with id: " + id));
        return modelMapper.map(cartItem, CartItemRespDTO.class);
    }

    @Override
    public CartItemRespDTO saveItem(CartItemReqDTO cartItemDto) {
        Cart cart = cartRepository.findById(cartItemDto.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: " + cartItemDto.getCartId()));

        Book book = bookRepository.findById(cartItemDto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + cartItemDto.getBookId()));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setBook(book);
        cartItem.setQty(cartItemDto.getQty());
        cartItem.setPrice(cartItemDto.getPrice());

        CartItem saved = cartItemRepository.save(cartItem);
        return modelMapper.map(saved, CartItemRespDTO.class);
    }

    @Override
    public CartItemRespDTO updateCartItem(CartItemReqDTO cartItemDto) {
        CartItem existing = cartItemRepository.findById(cartItemDto.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with ID: " + cartItemDto.getCartId()));

        Book book = bookRepository.findById(cartItemDto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + cartItemDto.getBookId()));

        existing.setBook(book);
        existing.setQty(cartItemDto.getQty());
        CartItem updated = cartItemRepository.save(existing);

        return modelMapper.map(updated, CartItemRespDTO.class);
    }

    @Override
    public void deleteCartItem(Long id) {
        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with ID: " + id));
        cartItemRepository.delete(item);
    }
}
