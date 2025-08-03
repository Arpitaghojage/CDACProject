package com.cdac.controller;

import com.cdac.dto.PaymentReqDTO;
import com.cdac.dto.PaymentRespDTO;
import com.cdac.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can create
    @PostMapping
    public ResponseEntity<PaymentRespDTO> createPayment(@RequestBody PaymentReqDTO paymentDto) {
        return ResponseEntity.ok(paymentService.createPayment(paymentDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentRespDTO> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can view all
    @GetMapping
    public ResponseEntity<List<PaymentRespDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok("Payment deleted successfully.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentRespDTO>> getPaymentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }
}
