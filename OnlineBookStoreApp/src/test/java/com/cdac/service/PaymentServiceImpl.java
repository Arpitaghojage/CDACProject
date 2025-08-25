package com.cdac.service;

import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dto.PaymentReqDTO;
import com.cdac.dto.PaymentRespDTO;
import com.cdac.entities.Order;
import com.cdac.entities.Payment;
import com.cdac.repository.OrderRepository;
import com.cdac.repository.PaymentRepository;
import com.razorpay.RazorpayClient;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Value("${razorpay.key_id}")
    private String razorpayKeyId;

    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentRespDTO createPayment(PaymentReqDTO paymentDto) {
        Payment payment = modelMapper.map(paymentDto, Payment.class);

        // Get order from orderId
        Order order = orderRepository.findById(paymentDto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + paymentDto.getOrderId()));

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

    @Override
    public String createRazorpayOrder(double amount, String currency, String receipt) throws Exception {
        RazorpayClient client = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int)(amount * 100)); // amount in paise
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", receipt);
        orderRequest.put("payment_capture", 1);
        com.razorpay.Order order = client.orders.create(orderRequest);
        return order.toString();
    }
}
