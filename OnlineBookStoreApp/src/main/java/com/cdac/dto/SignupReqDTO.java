package com.cdac.dto;

import com.cdac.entities.UserRole;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupReqDTO {

    @NotBlank(message = "first name must be supplied")
    private String userName;


    @NotBlank(message = "last name must be supplied")
    @Size(min = 4,max=20,message = "invalid last name length")
    private String fullName;


    @NotBlank(message = "Email must be supplied")
    @Email(message = "Invalid email format")
    public String email;


    @NotBlank
    @Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[#@$*]).{5,20})",message = "Invalid password format")
    private String password;


    @NotNull(message = "user role must be supplied")
    private UserRole userRole;



}
