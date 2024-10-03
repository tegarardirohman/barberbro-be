package com.enigmacamp.barbershop.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    private String id;

    private String firstName;

    private String surname;

    @JsonProperty("is_male")
    private Boolean isMale;

    @JsonProperty("date_of_birth")
    private Long dateOfBirth;

    private String email;

    private String phone;

    private String address;

    private String about;
}