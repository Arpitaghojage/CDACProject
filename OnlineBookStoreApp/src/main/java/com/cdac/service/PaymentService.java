package com.cdac.service;

import com.cdac.dto.PaymentReqDTO;
import com.cdac.dto.PaymentRespDTO;
import com.cdac.entities.Payment;

import java.util.List;

public interface PaymentService {

    PaymentRespDTO createPayment(PaymentReqDTO paymentDto);
    PaymentRespDTO getPaymentById(Long id);
    List<PaymentRespDTO> getAllPayments();
    void deletePayment(Long id);
    List<PaymentRespDTO> getPaymentsByStatus(String status);
}
