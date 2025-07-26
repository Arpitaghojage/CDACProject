package com.cdac.dto;

import com.cdac.entities.Book;
import com.cdac.entities.Cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRespDTO extends BaseDTO {

	private int qty;
	private double price;
	private Book book;
	private Cart cart;
}
