package com.cdac.dto;

import com.cdac.entities.Category;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookReqDTO {
	
	@NotNull
    @Size(max = 25, message = "Title can be at most 25 characters")
	private String title;
	
    @NotBlank(message = "Author is required")
    @Size(max = 25, message = "Author can be at most 25 characters")
	private String author;
	
	@Min(1)
	@NotNull(message = "Price is required")
	private double price;
	
    @Min(value = 0, message = "Stock cannot be negative")
	private int stock;
	
    @Size(max = 200, message = "Image URL can be at most 200 characters")
	private String imageUrl;
    
    @NotNull
    private Long categoryId;
	
}
