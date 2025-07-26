package com.cdac.dto;

import com.cdac.entities.Cart;
import com.cdac.entities.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartReqDTO {

	 @NotNull(message = "Quantity is required")
	 @Min(value = 1, message = "Quantity must be at least 1")
	 private int qty;
	 
	 @NotNull(message = "Price is required")
	 @Min(value = 1, message = "Price must be at least 1")
	 private Double price;
	 
	 @NotNull
	 private User user;

	@NotNull
	private Cart cart;


}
	  
