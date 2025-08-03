package com.cdac.dto;

import com.cdac.entities.PaymentMethod;
import com.cdac.entities.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentReqDTO {

    @NotNull(message = "Payment date is required")
    @PastOrPresent(message = "Payment date cannot be in the future")
    private LocalDate paymentDate;

    @DecimalMin(value = "0.1", inclusive = true, message = "Amount must be at least 0.1")
    private double amount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod method;

    @NotNull(message = "Payment status is required")
    private PaymentStatus status;

    @NotNull(message = "Order reference is required")
    private Long orderId;
}
