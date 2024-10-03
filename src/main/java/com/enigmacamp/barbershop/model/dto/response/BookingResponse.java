package com.enigmacamp.barbershop.model.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String booking_id;
    
    private CustomerResponse customer;

    private BarberResponse barber;

    private List<ServiceResponse> services;

    @JsonProperty("booking_date")
    private Long bookingDate;

    @JsonProperty("booking_time")
    private String bookingTime;

    private String status;

    @JsonProperty("midtrans_payment_url")
    private String midtransPaymentUrl;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("updated_at")
    private Long updatedAt;
}