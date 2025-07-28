package com.cdac.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignInDTO {
    private String email;
    private String password;
}
