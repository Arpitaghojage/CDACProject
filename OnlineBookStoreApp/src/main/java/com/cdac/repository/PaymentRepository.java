package com.cdac.repository;

import com.cdac.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

        List<Payment> findByStatus(String status);
        List<Payment> findByMethod(String method);


}
