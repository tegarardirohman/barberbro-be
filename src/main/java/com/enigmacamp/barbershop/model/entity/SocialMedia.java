package com.enigmacamp.barbershop.model.entity;

import com.enigmacamp.barbershop.model.dto.response.SocialMediaResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_social_media")
public class SocialMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String social_media_id;

    @ManyToOne
    @JoinColumn(name = "barbershop_id", referencedColumnName = "id", nullable = false)
    private Barbers barbershop_id;

    private String platform_name;
    private String platform_url;

    public SocialMediaResponse toResponse() {
        return SocialMediaResponse.builder()
                .social_media_id(social_media_id)
                .platform_name(platform_name)
                .platform_url(platform_url)
                .build();
    }
}