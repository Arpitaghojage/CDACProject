package com.cdac.dto;

import com.cdac.entities.Order;
import com.cdac.entities.PaymentMethod;
import com.cdac.entities.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentRespDTO extends BaseDTO {

    private LocalDate paymentDate;
    private double amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private Order order;
}
