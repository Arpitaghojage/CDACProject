package com.cdac.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartReqDTO {
	 
	 @NotNull
	 private Long userId;

}
	  
