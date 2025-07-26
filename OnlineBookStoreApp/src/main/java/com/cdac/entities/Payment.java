package com.cdac.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="payment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Payment extends BaseEntity {

    @Column(name="payment_date")
    private LocalDate paymentDate;

    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_method")
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_status")
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name="order_id",nullable=false)
    private Order order;
}
