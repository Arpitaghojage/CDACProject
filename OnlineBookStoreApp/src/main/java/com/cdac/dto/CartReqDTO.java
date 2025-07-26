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
	 
	 @NotNull
	 private Long userId;

}
	  
