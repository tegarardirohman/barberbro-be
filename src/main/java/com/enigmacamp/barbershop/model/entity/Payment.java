package com.enigmacamp.barbershop.model.entity;

import com.enigmacamp.barbershop.model.dto.response.PaymentResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String payment_id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking bookingId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "midtrans_payment_url", nullable = true)
    private String midtransPaymentUrl;

    @Column(name = "transaction_date", nullable = false)
    private Long transactionDate;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    public PaymentResponse toResponse() {
        return PaymentResponse.builder()
                .paymentId(payment_id)
                .bookingId(bookingId.getBookingId())
                .amount(amount)
                .paymentMethod(paymentMethod)
                .paymentStatus(paymentStatus)
                .midtransPaymentUrl(midtransPaymentUrl)
                .transactionDate(transactionDate)
                .createdAt(createdAt)
                .build();
    }
}