package com.cdac.dto;

import com.cdac.entities.Cart;
import com.cdac.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRespDTO extends BaseDTO {

	 private User user;

}
