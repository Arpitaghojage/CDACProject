package com.cdac.service;

import com.cdac.dto.ForgotPasswordDTO;
import com.cdac.dto.ResetPasswordDTO;

public interface PasswordResetService {
    
    String initiatePasswordReset(ForgotPasswordDTO forgotPasswordDTO);
    
    String resetPassword(ResetPasswordDTO resetPasswordDTO);
    
    void cleanupExpiredTokens();
}
