package com.cdac.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Book extends BaseEntity {

	@Column(length=100)
	private String title;
	
	@Column(length=100)
	private String author;
	
	@Min(1)
	private double price;
	
	private int stock;

	@Column(length=255)
	private String imagePath;
	
	@Lob
	private byte[] imageUrl;
	
	@ManyToOne
	@JoinColumn(name="category_id",nullable=false)
	private Category category;
	
	@OneToMany(mappedBy = "book",cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<CartItem> cartItems = new ArrayList<>(); 
	
	@OneToMany(mappedBy = "book",cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<OrderItem> orderItems = new ArrayList<>();

}

