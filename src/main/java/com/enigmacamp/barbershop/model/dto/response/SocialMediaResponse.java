package com.enigmacamp.barbershop.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SocialMediaResponse {
    private String social_media_id;
    private String platform_name;
    private String platform_url;
}