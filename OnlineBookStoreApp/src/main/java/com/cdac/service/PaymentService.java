package com.cdac.service;

import com.cdac.entities.Payment;

import java.util.List;

public interface PaymentService {

    Payment createPayment(Payment payment);
    Payment getPaymentById(Long id);
    List<Payment> getAllPayments();
    void deletePayment(Long id);
    List<Payment> getPaymentsByStatus(String status);
}
