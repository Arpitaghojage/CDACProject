package com.cdac.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cartItems")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartItem extends BaseEntity{

	@Column(name="Quantity")
	private int qty;

	@Min(value = 1, message = "Price must be greater than or equal to 1")
	private double price;
	
	@ManyToOne
	@JoinColumn(name="book_id",nullable=false)
	private Book book;
	
	@ManyToOne
	@JoinColumn(name="cart_id",nullable=false)
	private Cart cart;
}
