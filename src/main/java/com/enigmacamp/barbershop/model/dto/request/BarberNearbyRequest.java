package com.enigmacamp.barbershop.model.dto.request;

import lombok.Data;

@Data
public class BarberNearbyRequest {
    private double latitude;
    private double longitude;
}