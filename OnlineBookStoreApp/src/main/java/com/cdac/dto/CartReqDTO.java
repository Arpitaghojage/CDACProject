package com.cdac.dto;

import com.cdac.entities.Book;
import com.cdac.entities.Cart;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartReqDTO {

	 @NotNull(message = "Quantity is required")
	 @Min(value = 1, message = "Quantity must be at least 1")
	 private int qty;
	 
	 @NotNull(message = "Price is required")
	 @Min(value = 1, message = "Price must be at least 1")
	 private Double price;
	 
	 @NotNull
	 private Book book;
	 
	 @NotNull
	 private Cart cart;

}
	  
