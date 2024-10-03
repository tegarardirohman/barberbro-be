package com.enigmacamp.barbershop.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GalleryImageResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String image_id;

    private String barbers_id;

    private String name;

    private String path;

    private Long size;

    private String contentType;
}