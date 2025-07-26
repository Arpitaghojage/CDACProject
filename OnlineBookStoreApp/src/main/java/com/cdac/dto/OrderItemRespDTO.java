package com.cdac.dto;

import com.cdac.entities.Book;
import com.cdac.entities.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRespDTO extends BaseDTO {

	
	private int qty;
	private double price;
	private Book book;
	private Order order;

}
