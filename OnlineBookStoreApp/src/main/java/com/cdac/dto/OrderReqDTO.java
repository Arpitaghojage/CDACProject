package com.cdac.dto;

import java.time.LocalDate;

import com.cdac.entities.User;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReqDTO {


	@NotNull(message = "Order date is required")
    @PastOrPresent(message = "Order date cannot be in the future")
	private LocalDate orderDate;
	
    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be greater than 0")
	private double totalAmount;
	
    @NotNull(message = "User is required")
	private Long userId;
}
