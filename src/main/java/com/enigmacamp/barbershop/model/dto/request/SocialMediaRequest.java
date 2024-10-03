package com.enigmacamp.barbershop.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SocialMediaRequest {
    @JsonProperty("social_media_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String socialMediaId;
    @JsonProperty("platform_name")
    private String platform_name;
    @JsonProperty("platform_url")
    private String platform_url;
}