package com.cdac.dto;


import com.cdac.entities.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRespDTO extends BookReqDTO {

	private String title;
	private String author;
	private double price;
	private int stock;
	private byte[] imageUrl;
	private Category category;
	
	
}
