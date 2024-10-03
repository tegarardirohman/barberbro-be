package com.enigmacamp.barbershop.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse<T> {
    private Integer statusCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
