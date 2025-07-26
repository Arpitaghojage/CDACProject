package com.cdac.dto;

import com.cdac.entities.Book;
import com.cdac.entities.Cart;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemReqDTO {

	 @Min(value = 1, message = "Quantity must be at least 1")
	    private int qty;

	    @NotNull(message = "Price is required")
	    @Min(value = 1, message = "Price must be at least 1")
	    private Double price;

	    @NotNull(message = "Book ID must not be null")
		private Book book;

	    @NotNull(message = "Cart ID must not be null")
	    private Cart cart;

	
}
