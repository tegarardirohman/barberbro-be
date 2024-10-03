package com.enigmacamp.barbershop.model.dto.response;

import java.util.List;

import com.enigmacamp.barbershop.model.entity.Barbers;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.enigmacamp.barbershop.model.entity.BarberProfilePicture;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BarberResponse {
    private String id;
    private String name;
    private String contact_number;
    private String email;
    private String street_address;
    private String city;
    private String state_province_region;
    private String postal_zip_code;
    private String country;
    private Double latitude;
    private Double longitude;
    private String description;
    // private Users userId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float balance;
    private Boolean verified;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("average_rating")
    private Double averageRating;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("review_count")
    private Long reviewCount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BarberProfilePicture barbershop_profile_picture_id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OperationalHourResponse> operational_hours;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ServiceResponse> services;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SocialMediaResponse> social_media;
    @JsonProperty("distance_km")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double distanceKm;
    private Long createdAt;
    private Long updateAt;

    public Barbers toEntity() {
        return Barbers.builder()
                .id(id)
                .name(name)
                .contact_number(contact_number)
                .email(email)
                .street_address(street_address)
                .city(city)
                .state_province_region(state_province_region)
                .postal_zip_code(postal_zip_code)
                .country(country)
                .latitude(latitude)
                .longitude(longitude)
                .description(description)
                // .userId(userId)
                // .balance(balance)
                .verified(verified)
                // .barbershop_profile_picture_id(barbershop_profile_picture_id)
                // .operationalHours(operational_hours)
                // .services(services)
                // .social_media(social_media)
                .createdAt(createdAt)
                .updateAt(updateAt)
                .build();
    }
}
