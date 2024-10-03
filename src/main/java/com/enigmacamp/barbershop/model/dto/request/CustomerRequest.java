package com.enigmacamp.barbershop.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CustomerRequest {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    private String firstName;

    private String surname;

    @JsonProperty("is_male")
    private Boolean isMale;

    @JsonProperty("date_of_birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Long dateOfBirth;

    // private String email;

    private String phone;

    private String address;

    private String about;
}