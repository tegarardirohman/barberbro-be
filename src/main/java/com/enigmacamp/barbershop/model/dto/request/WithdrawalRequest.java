package com.enigmacamp.barbershop.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WithdrawalRequest {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    // @JsonProperty("barber_id")
    // private String barberId;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("withdrawal_amount")
    private Double withdrawalAmount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;
}