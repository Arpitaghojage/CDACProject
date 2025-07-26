package com.cdac.dto;

import com.cdac.entities.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserReqDTO {


	@NotBlank(message = "Username is required")
    @Size(max = 25, message = "Username must be at most 25 characters")
	private String userName;
	
	@NotBlank(message = "Full name is required")
    @Size(max = 25, message = "Full name must be at most 25 characters")
	private String fullName;
	
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
	@Size(max = 25, message = "Email must be at most 25 characters")
	private String email;
	
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 25, message = "Password must be between 6 and 25 characters")
	private String password;
	
    @NotNull(message = "User role is required")
	private UserRole role;
}
