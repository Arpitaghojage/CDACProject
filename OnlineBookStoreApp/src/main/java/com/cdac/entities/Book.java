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
@Table(name="books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Book extends BaseEntity {

	@Column(length=25)
	private String title;
	
	@Column(length=25)
	private String author;
	
	@Min(1)
	private double price;
	
	private int stock;
	
	@Column(length=200)
	private String imageUrl;
	
	@ManyToOne
	@JoinColumn(name="category_id",nullable=false)
	private Category category;
	
}
