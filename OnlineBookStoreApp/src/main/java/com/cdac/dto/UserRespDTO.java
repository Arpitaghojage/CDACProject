package com.cdac.dto;

import com.cdac.entities.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRespDTO  extends BaseDTO{
	
	private String userName;
	private String fullName;
	private String email;
	private String password;
	private UserRole role;

}
