package com.enigmacamp.barbershop.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MidtransWebhookRequest {
    @JsonProperty("transaction_status")
    private String transactionStatus;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("gross_amount")
    private Double grossAmount;

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("transaction_time")
    private String transactionTime;

    @JsonProperty("signature_key")
    private String signatureKey;

    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("status_message")
    private String statusMessage;

    @JsonProperty("settlement_time")
    private String settlementTime;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("fraud_status")
    private String fraudStatus;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("expiry_time")
    private String expiryTime;

    private String issuer;

    private String acquirer;

    private String currency;
}