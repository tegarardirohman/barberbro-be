package com.enigmacamp.barbershop.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    @JsonProperty("payment_id")
    private String paymentId;
    
    @JsonProperty("booking_id")
    private String bookingId;

    private Double amount;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("midtrans_payment_url")
    private String midtransPaymentUrl;

    @JsonProperty("transaction_date")
    private Long transactionDate;

    @JsonProperty("created_at")
    private Long createdAt;
}