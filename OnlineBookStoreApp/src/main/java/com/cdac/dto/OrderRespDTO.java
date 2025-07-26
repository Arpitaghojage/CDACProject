package com.cdac.dto;

import java.time.LocalDate;

import com.cdac.entities.User;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderRespDTO  extends BaseDTO{
	
	private LocalDate orderDate;
	private double totalAmount;
	private Long userId;

}
