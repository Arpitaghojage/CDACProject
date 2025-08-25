package com.cdac.service;

import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dto.ForgotPasswordDTO;
import com.cdac.dto.ResetPasswordDTO;
import com.cdac.entities.PasswordResetToken;
import com.cdac.entities.User;
import com.cdac.repository.PasswordResetTokenRepository;
import com.cdac.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@AllArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String initiatePasswordReset(ForgotPasswordDTO forgotPasswordDTO) {
        // Check if user exists by email
        Optional<User> userOptional = userRepository.findByEmail(forgotPasswordDTO.getEmail());
        if (userOptional.isEmpty()) {
            // Don't reveal if email exists or not for security
            return "If the email exists, an OTP has been sent.";
        }

        User user = userOptional.get();
        
        // Generate 6-digit OTP
        String otp = generateOTP();
        
        // Set expiry time (10 minutes from now)
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(10);
        
        // Save OTP
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setEmail(user.getEmail());
        resetToken.setOtp(otp);
        resetToken.setExpiryDate(expiryDate);
        resetToken.setUsed(false);
        
        tokenRepository.save(resetToken);
        
        // In a real application, you would send email here
        // For demo purposes, we'll just print the OTP
        System.out.println("OTP for " + user.getEmail() + ": " + otp);
        
        return "If the email exists, an OTP has been sent.";
    }

    @Override
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) {
        // Find OTP by email
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByEmailAndOtpAndUsedFalse(
            resetPasswordDTO.getEmail(), resetPasswordDTO.getOtp());
        if (tokenOptional.isEmpty()) {
            throw new ResourceNotFoundException("Invalid or expired OTP");
        }

        PasswordResetToken token = tokenOptional.get();
        
        // Check if OTP is expired
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ResourceNotFoundException("OTP has expired");
        }
        
        // Check if OTP is already used
        if (token.isUsed()) {
            throw new ResourceNotFoundException("OTP has already been used");
        }
        
        // Find user by email
        Optional<User> userOptional = userRepository.findByEmail(token.getEmail());
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        User user = userOptional.get();
        
        // Update password
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userRepository.save(user);
        
        // Mark OTP as used
        token.setUsed(true);
        tokenRepository.save(token);
        
        return "Password has been reset successfully";
    }

    @Override
    public void cleanupExpiredTokens() {
        tokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }
    
    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generates 6-digit number
        return String.valueOf(otp);
    }
}
