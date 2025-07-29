package com.cdac.controller;

import com.cdac.dto.AuthResponse;
import com.cdac.dto.SignInDTO;
import com.cdac.dto.SignupReqDTO;
import com.cdac.security.JwtUtils;
import com.cdac.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Validated
public class UserSignInSignUpController {

    private final UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    @Operation(description = "User sign in ")
    public ResponseEntity<?> userSignIn(@RequestBody SignInDTO dto) {
        System.out.println("in sign in "+dto);

        Authentication authToken=	new
                UsernamePasswordAuthenticationToken(dto.getEmail(),
                dto.getPassword());
        System.out.println("before - "+authToken.isAuthenticated());//false
        Authentication validAuth =
                authenticationManager.authenticate(authToken);

        System.out.println("after - "+validAuth.isAuthenticated());//true
        System.out.println(validAuth);//user details : UserEntity


        return ResponseEntity.status(HttpStatus.CREATED)//SC 201
                .body(new AuthResponse("Succesful login !",
                        jwtUtils.generateJwtToken(validAuth)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody @Valid
                                            SignupReqDTO dto)
    {
        System.out.println("in signup "+dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.signUp(dto));

    }
}
