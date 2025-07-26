package com.cdac.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryReqDTO {


	@NotBlank(message = "Category name is required")
    @Size(max = 50, message = "Category name must not exceed 50 characters")
	private String categoryName;
	
}
