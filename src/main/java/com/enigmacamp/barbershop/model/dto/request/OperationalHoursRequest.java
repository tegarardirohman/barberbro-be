package com.enigmacamp.barbershop.model.dto.request;

import com.enigmacamp.barbershop.constant.Days;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OperationalHoursRequest {
    @JsonProperty("operating_hours_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String operatingHourId;
    @JsonProperty("day")
    private Days day;
    @JsonProperty("opening_time")
    private String opening_time;
    @JsonProperty("closing_time")
    private String closing_time;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("limit_per_session")
    private Integer limitPerSession;
}