package com.cdac.dto;

import com.cdac.entities.Book;
import com.cdac.entities.Order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderItemReqDTO {
	
	@NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
	private int qty;
	
    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be at least 1")
	private double price;
	
    @NotNull(message = "Book ID is required")
	private Book book;
	
    @NotNull(message = "Order ID is required")
	private Order order;
}
