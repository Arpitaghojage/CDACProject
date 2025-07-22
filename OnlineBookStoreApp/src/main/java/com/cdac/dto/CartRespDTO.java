package com.cdac.dto;

import com.cdac.entities.Book;
import com.cdac.entities.Cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRespDTO extends BaseDTO {

	 private int qty;
	 private Double price;
	 private Book book;
	 private Cart cart;
}
