package com.enigmacamp.barbershop.model.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MidtransRequest {

    @JsonProperty("transaction_details")
    private TransactionDetails transaction_details;

    @JsonProperty("credit_card")
    private CreditCard creditCard;

    @JsonProperty("customer_details")
    private CustomerDetails customerDetails;

    @JsonProperty("item_details")
    private List<ItemDetails> itemDetails;

    @JsonProperty("enabled_payments")
    private List<String> enabledPayments;

    @Data
    @Builder
    public static class TransactionDetails {
        @JsonProperty("order_id")
        private String orderId;

        @JsonProperty("gross_amount")
        private Double grossAmount;
    }

    @Data
    @Builder
    public static class CreditCard {
        private Boolean secure;
    }

    @Data
    @Builder
    public static class CustomerDetails {
        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        private String email;
        private String phone;
    }

    @Data
    @Builder
    public static class ItemDetails {
        private String id;
        private Double price;
        private Integer quantity;
        private String name;
    }

}