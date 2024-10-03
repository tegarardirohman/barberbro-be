package com.enigmacamp.barbershop.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WithdrawalResponse {
    private String id;
    private BarberResponse barber;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("account_name")
    private String accountName;
    @JsonProperty("bank_name")
    private String bankName;
    @JsonProperty("withdrawal_amount")
    private Double withdrawalAmount;
    private String status;
    @JsonProperty("created_at")
    private Long createdAt;
}