package com.enigmacamp.barbershop.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PaymentRequest {
    @JsonProperty("booking_id")
    private String bookingId;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("transaction_date")
    private Long transactionDate;

    @JsonProperty("created_at")
    private Long createdAt;
}