package com.cdac.service;

import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dto.PaymentReqDTO;
import com.cdac.dto.PaymentRespDTO;
import com.cdac.entities.Order;
import com.cdac.entities.Payment;
import com.cdac.repository.OrderRepository;
import com.cdac.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private ModelMapper modelMapper;

    @Override
    public PaymentRespDTO createPayment(PaymentReqDTO paymentDto) {
        Payment payment = modelMapper.map(paymentDto, Payment.class);

        // Get order from orderId
        Order order = orderRepository.findById(paymentDto.getOrder().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + paymentDto.getOrder().getId()));

        payment.setOrder(order);
        Payment saved = paymentRepository.save(payment);
        return modelMapper.map(saved, PaymentRespDTO.class);
    }

    @Override
    public PaymentRespDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));
        return modelMapper.map(payment, PaymentRespDTO.class);
    }

    @Override
    public List<PaymentRespDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(payment -> modelMapper.map(payment, PaymentRespDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));
        paymentRepository.delete(payment);
    }

    @Override
    public List<PaymentRespDTO> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status)
                .stream()
                .map(payment -> modelMapper.map(payment, PaymentRespDTO.class))
                .collect(Collectors.toList());
    }
}
