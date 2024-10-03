package com.enigmacamp.barbershop.model.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class BarberRegisterRequest {
    private BarberRequest barbershop;
    private List<OperationalHoursRequest> operational_hours;
    private List<ServicesRequest> services;
    private List<SocialMediaRequest> social_media;
}
