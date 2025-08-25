package com.cdac.controller;

import com.cdac.dto.ForgotPasswordDTO;
import com.cdac.dto.ResetPasswordDTO;
import com.cdac.service.PasswordResetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Validated
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Validated ForgotPasswordDTO forgotPasswordDTO) {
        String message = passwordResetService.initiatePasswordReset(forgotPasswordDTO);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Validated ResetPasswordDTO resetPasswordDTO) {
        String message = passwordResetService.resetPassword(resetPasswordDTO);
        return ResponseEntity.ok(message);
    }
}
