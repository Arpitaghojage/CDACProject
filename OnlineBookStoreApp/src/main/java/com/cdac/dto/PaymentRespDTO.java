package com.cdac.dto;

import com.cdac.entities.Order;
import com.cdac.entities.PaymentMethod;
import com.cdac.entities.PaymentStatus;

import java.time.LocalDate;

public class PaymentRespDTO extends BaseDTO {

    private LocalDate paymentDate;
    private double amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private Order order;
}
