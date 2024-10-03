package com.enigmacamp.barbershop.model.dto.request;

import com.enigmacamp.barbershop.model.entity.BarberProfilePicture;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class BarberRequest {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    private String name;
    private String contact_number;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String role;
    private String city;
    private String street_address;
    private String state_province_region;
    private String postal_zip_code;
    private String country;
    private Double latitude;
    private Double longitude;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BarberProfilePicture barbershop_profile_picture_id;
}
